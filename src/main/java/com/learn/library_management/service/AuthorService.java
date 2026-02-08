package com.learn.library_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.library_management.dto.AuthorCreateDTO;
import com.learn.library_management.dto.AuthorResponseDTO;
import com.learn.library_management.dto.AuthorUpdateDTO;
import com.learn.library_management.entities.Author;
import com.learn.library_management.events.WelcomeEmailEvent;
import com.learn.library_management.exception.AuthorAlreadyExistsException;
import com.learn.library_management.exception.AuthorHasBooksException;
import com.learn.library_management.exception.AuthorNotFoundException;
import com.learn.library_management.mapper.AuthorMapper;
import com.learn.library_management.rabbitconfig.RabbitConstants;
import com.learn.library_management.repository.AuthorRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    @CachePut(value = "authors", key = "#result.authorId")
    public AuthorResponseDTO createAuthor(@Valid AuthorCreateDTO dto) {
        authorRepository.findByEmail(dto.getEmail()).ifPresent(existing -> {
            throw new AuthorAlreadyExistsException();
        });

        Author author = authorMapper.toEntity(dto);
        Author saved = authorRepository.save(author);

        WelcomeEmailEvent event = new WelcomeEmailEvent(
            author.getName(),
            author.getEmail(),
            "Author"
        );

        rabbitTemplate.convertAndSend(
            RabbitConstants.MAIL_EXCHANGE,
            RabbitConstants.WELCOME_KEY,
            event
        );

        return authorMapper.toDTO(saved);
    }

    @Transactional
    @CachePut(value = "authors", key = "#authorId")
    public AuthorResponseDTO updateAuthor(Long authorId, @Valid AuthorUpdateDTO dto) {
        Author existing = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException());

        authorRepository.findByEmail(dto.getEmail())
            .filter(a -> !a.getAuthorId().equals(authorId))
            .ifPresent(a -> {
                throw new AuthorAlreadyExistsException();
            });

        existing.setName(dto.getName());
        existing.setBio(dto.getBio());
        existing.setEmail(dto.getEmail());

        Author updated = authorRepository.save(existing);
        return authorMapper.toDTO(updated);
    }

    @Transactional
    @Cacheable(value = "authors", key = "#authorId")
    public AuthorResponseDTO findById(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException());
        return authorMapper.toDTO(author);
    }

    @Transactional
    @Cacheable(value = "authors")
    public Page<AuthorResponseDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorsPage = authorRepository.findAll(pageable);
        return authorsPage.map(authorMapper::toDTO);
    }

    @Transactional
    @CacheEvict(value = "authors", key = "#authorId")
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException());
        
        if (!author.getBooks().isEmpty()) {
            throw new AuthorHasBooksException();
        }
        authorRepository.delete(author);
    }
    
	@Transactional
	@Cacheable(value = "authors", key = "'title_' + #title")
	public List<AuthorResponseDTO> searchAuthorByName(String title) {
		return authorRepository.findByNameContainingIgnoreCase(title).stream().map(authorMapper::toDTO)
				.collect(Collectors.toList());
	}
}
