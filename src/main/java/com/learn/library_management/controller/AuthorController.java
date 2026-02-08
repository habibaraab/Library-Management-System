package com.learn.library_management.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BasicResponse;
import com.learn.library_management.dto.AuthorCreateDTO;
import com.learn.library_management.dto.AuthorResponseDTO;
import com.learn.library_management.dto.AuthorUpdateDTO;
import com.learn.library_management.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Author Controller", description = "API for managing library authors.")
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Create a new author", description = "Allows admin to create a new author")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> createAuthor(@RequestBody AuthorCreateDTO dto) {
        AuthorResponseDTO response = authorService.createAuthor(dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_AUTHOR, response));
    }

    @Operation(summary = "Update author", description = "Allows admin to update author information")
    @PutMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> updateAuthor(
            @PathVariable Long authorId,
            @RequestBody AuthorUpdateDTO dto) {
        AuthorResponseDTO response = authorService.updateAuthor(authorId, dto);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_AUTHOR, response));
    }

    @Operation(summary = "Find author by ID", description = "Retrieve details of a specific author")
    @GetMapping("/{authorId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getAuthorById(@PathVariable Long authorId) {
        AuthorResponseDTO response = authorService.findById(authorId);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_AUTHOR, response));
    }

    @Operation(summary = "Get all authors", description = "Retrieve a paginated list of all authors")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AuthorResponseDTO> response = authorService.findAll(page, size);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_AUTHORS, response));
    }

    @Operation(summary = "Delete author", description = "Allows admin to delete an author by ID")
    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_AUTHOR));
    }
    
    @Operation(summary = "Search authors by name", description = "Find authors that contain a given name")
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> searchAuthorByName(@RequestParam String title) {
        List<AuthorResponseDTO> response = authorService.searchAuthorByName(title);
        return ResponseEntity.ok(new BasicResponse(Messages.SEARCH_AUTHORS, response));
    }
}
