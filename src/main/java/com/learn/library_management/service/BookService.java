package com.learn.library_management.service;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BookCreateDTO;
import com.learn.library_management.dto.BookResponseDTO;
import com.learn.library_management.dto.BookUpdateDTO;
import com.learn.library_management.entities.Author;
import com.learn.library_management.entities.Book;
import com.learn.library_management.entities.Category;
import com.learn.library_management.entities.Publisher;
import com.learn.library_management.exception.*;
import com.learn.library_management.mapper.BookMapper;
import com.learn.library_management.repository.AuthorRepository;
import com.learn.library_management.repository.BookRepository;
import com.learn.library_management.repository.BorrowingTransactionRepository;
import com.learn.library_management.repository.CategoryRepository;
import com.learn.library_management.repository.PublisherRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final PublisherRepository publisherRepository;
	private final CategoryRepository categoryRepository;
	private final AuthorRepository authorRepository;
	private final BorrowingTransactionRepository borrowingTransactionRepository;
	private final BookMapper bookMapper;
	private final ImageService imageService;

	@Transactional
	@CachePut(value = "books", key = "#result.bookId")
	public BookResponseDTO createBook(@Valid BookCreateDTO dto, MultipartFile coverImage) throws Exception {
		if (bookRepository.existsByIsbn(dto.getIsbn())) {
			throw new BookAlreadyExistsException();
		}

		Book book = bookMapper.toEntity(dto);

		Publisher publisher = publisherRepository.findById(dto.getPublisherId())
				.orElseThrow(PublisherNotFoundException::new);
		book.setPublisher(publisher);

		Category category = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(CategoryNotFoundException::new);
		book.setCategory(category);

		if (dto.getAuthorIds() != null) {
			if (dto.getAuthorIds().isEmpty()) {
				book.setAuthors(new ArrayList<>());
			} else {
				List<Author> authors = authorRepository.findAllById(dto.getAuthorIds());
				if (authors.isEmpty())
					throw new AuthorNotFoundException();
				book.setAuthors(authors);
			}
		}

		if (coverImage != null && !coverImage.isEmpty()) {
			try {
				String imageUrl = imageService.uploadImage(coverImage);
				book.setCoverImage(imageUrl);
			} catch (IOException e) {
				throw new Exception(Messages.UPLOAD_IMAGE_FAILED + e.getMessage());
			}
		}

		Book saved = bookRepository.save(book);
		return bookMapper.toDTO(saved);
	}

	@Transactional
	@CachePut(value = "books", key = "#bookId")
	public BookResponseDTO updateBook(Long bookId, @Valid BookUpdateDTO dto, MultipartFile coverImage)
			throws Exception {
		Book existing = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

		if (!existing.getIsbn().equals(dto.getIsbn()) && bookRepository.existsByIsbn(dto.getIsbn())) {
			throw new BookAlreadyExistsException();
		}

		existing.setTitle(dto.getTitle());
		existing.setIsbn(dto.getIsbn());
		existing.setSummary(dto.getSummary());
		existing.setLanguage(dto.getLanguage());
		existing.setPublicationYear(dto.getPublicationYear());
		existing.setEdition(dto.getEdition());

		Publisher publisher = publisherRepository.findById(dto.getPublisherId())
				.orElseThrow(PublisherNotFoundException::new);
		existing.setPublisher(publisher);

		Category category = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(CategoryNotFoundException::new);
		existing.setCategory(category);

		if (dto.getAuthorIds() != null) {
			if (dto.getAuthorIds().isEmpty()) {
				existing.setAuthors(new ArrayList<>());
			} else {
				List<Author> authors = authorRepository.findAllById(dto.getAuthorIds());
				if (authors.isEmpty())
					throw new AuthorNotFoundException();
				existing.setAuthors(authors);
			}
		}

		if (coverImage != null && !coverImage.isEmpty()) {
			try {
				String imageUrl = imageService.uploadImage(coverImage);
				existing.setCoverImage(imageUrl);
			} catch (IOException e) {
				throw new Exception(Messages.UPLOAD_IMAGE_FAILED + e.getMessage());
			}
		}

		Book updated = bookRepository.save(existing);
		return bookMapper.toDTO(updated);
	}

	@Transactional
	@CacheEvict(value = "books", key = "#bookId")
	public void deleteBook(Long bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

		if (borrowingTransactionRepository.existsByBook_BookId(bookId)) {
			throw new BookDeletionNotAllowedException();
		}

		bookRepository.delete(book);
	}

	@Transactional
	@Cacheable(value = "books", key = "#bookId")
	public BookResponseDTO getBookById(Long bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
		return bookMapper.toDTO(book);
	}

	@Transactional
	@Cacheable(value = "books")
	public Page<BookResponseDTO> getAllBooks(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Book> bookPage = bookRepository.findAll(pageable);
		return bookPage.map(bookMapper::toDTO);
	}

	@Transactional
	@Cacheable(value = "books", key = "'title_' + #title")
	public List<BookResponseDTO> searchBooksByTitle(String title) {
		return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(bookMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	@Cacheable(value = "books", key = "'category_' + #categoryId")
	public List<BookResponseDTO> getBooksByCategory(Long categoryId) {
		return bookRepository.findByCategory_CategoryId(categoryId).stream().map(bookMapper::toDTO)
				.collect(Collectors.toList());
	}
}