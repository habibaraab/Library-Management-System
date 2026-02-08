package com.learn.library_management.controller;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BasicResponse;
import com.learn.library_management.dto.BookCreateDTO;
import com.learn.library_management.dto.BookResponseDTO;
import com.learn.library_management.dto.BookUpdateDTO;
import com.learn.library_management.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Book Controller", description = "API for managing books in the library.")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Create a new book", description = "Allows admin or librarian to create a new book")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> createBook(
            @RequestPart("book") BookCreateDTO dto,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage) throws Exception {
        BookResponseDTO response = bookService.createBook(dto, coverImage);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_BOOK, response));
    }

    @Operation(summary = "Update a book", description = "Allows admin or librarian to update an existing book")
    @PutMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> updateBook(
            @PathVariable Long bookId,
            @RequestPart("book") BookUpdateDTO dto,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage) throws Exception {
        BookResponseDTO response = bookService.updateBook(bookId, dto, coverImage);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_BOOK, response));
    }

    @Operation(summary = "Delete a book", description = "Allows admin or librarian to delete a book by ID")
    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_BOOK));
    }

    @Operation(summary = "Get book by ID", description = "Retrieve details of a specific book")
    @GetMapping("/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getBookById(@PathVariable Long bookId) {
        BookResponseDTO response = bookService.getBookById(bookId);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_BOOK, response));
    }

    @Operation(summary = "Get all books", description = "Retrieve all books in the library")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BookResponseDTO> response = bookService.getAllBooks(page, size);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_BOOKS, response));
    }

    @Operation(summary = "Search books by title", description = "Find books that contain a given title")
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> searchBooksByTitle(@RequestParam String title) {
        List<BookResponseDTO> response = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(new BasicResponse(Messages.SEARCH_BOOKS, response));
    }

    @Operation(summary = "Get books by category", description = "Retrieve books belonging to a specific category")
    @GetMapping("/category/{categoryId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getBooksByCategory(@PathVariable Long categoryId) {
        List<BookResponseDTO> response = bookService.getBooksByCategory(categoryId);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_BOOKS_BY_CATEGORY, response));
    }
}