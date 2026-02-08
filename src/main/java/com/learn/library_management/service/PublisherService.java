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

import com.learn.library_management.dto.PublisherCreateDTO;
import com.learn.library_management.dto.PublisherResponseDTO;
import com.learn.library_management.dto.PublisherUpdateDTO;
import com.learn.library_management.entities.Publisher;
import com.learn.library_management.events.WelcomeEmailEvent;
import com.learn.library_management.exception.PublisherNotFoundException;
import com.learn.library_management.exception.PublisherAlreadyExistsException;
import com.learn.library_management.exception.PublisherHasBooksException;
import com.learn.library_management.mapper.PublisherMapper;
import com.learn.library_management.rabbitconfig.RabbitConstants;
import com.learn.library_management.repository.PublisherRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class PublisherService {

	private final PublisherRepository publisherRepository;
	private final PublisherMapper publisherMapper;
	private final RabbitTemplate rabbitTemplate;

	@Transactional
	@CachePut(value = "publishers", key = "#result.publisherId")
	public PublisherResponseDTO createPublisher(@Valid PublisherCreateDTO dto) {
		publisherRepository.findByEmail(dto.getEmail()).ifPresent(existing -> {
			throw new PublisherAlreadyExistsException();
		});
		Publisher publisher = publisherMapper.toEntity(dto);
		Publisher saved = publisherRepository.save(publisher);

		WelcomeEmailEvent event = new WelcomeEmailEvent(publisher.getName(), publisher.getEmail(), "Publisher");

		rabbitTemplate.convertAndSend(RabbitConstants.MAIL_EXCHANGE, RabbitConstants.WELCOME_KEY, event);
		return publisherMapper.toDTO(saved);
	}

	@Transactional
	@CachePut(value = "publishers", key = "#publisherId")
	public PublisherResponseDTO updatePublisher(Long publisherId, @Valid PublisherUpdateDTO dto) {
		Publisher existing = publisherRepository.findById(publisherId).orElseThrow(PublisherNotFoundException::new);

		publisherRepository.findByEmail(dto.getEmail()).filter(m -> !m.getPublisherId().equals(publisherId))
				.ifPresent(m -> {
					throw new PublisherAlreadyExistsException();
				});

		existing.setName(dto.getName());
		existing.setAddress(dto.getAddress());
		existing.setPhone(dto.getPhone());
		existing.setEmail(dto.getEmail());

		Publisher updated = publisherRepository.save(existing);
		return publisherMapper.toDTO(updated);
	}

	@Transactional
	@Cacheable(value = "publishers", key = "#publisherId")
	public PublisherResponseDTO findById(Long publisherId) {
		Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(PublisherNotFoundException::new);
		return publisherMapper.toDTO(publisher);
	}

	@Transactional
	@Cacheable(value = "publishers")
	public Page<PublisherResponseDTO> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Publisher> publishersPage = publisherRepository.findAll(pageable);
		return publishersPage.map(publisherMapper::toDTO);
	}

	@Transactional
	@CacheEvict(value = "publishers", key = "#publisherId")
	public void deletePublisher(Long publisherId) {
		Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(PublisherNotFoundException::new);

		if (!publisher.getBooks().isEmpty()) {
			throw new PublisherHasBooksException();
		}

		publisherRepository.delete(publisher);
	}
	
	@Transactional
	@Cacheable(value = "publishers", key = "'title_' + #title")
	public List<PublisherResponseDTO> searchPublisherByName(String title) {
		return publisherRepository.findByNameContainingIgnoreCase(title).stream().map(publisherMapper::toDTO)
				.collect(Collectors.toList());
	}
}
