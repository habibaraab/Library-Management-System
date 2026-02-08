package com.learn.library_management.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BasicResponse;
import com.learn.library_management.dto.PublisherCreateDTO;
import com.learn.library_management.dto.PublisherResponseDTO;
import com.learn.library_management.dto.PublisherUpdateDTO;
import com.learn.library_management.service.PublisherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Publisher Controller", description = "API for managing book publishers.")
@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @Operation(summary = "Create a new publisher", description = "Allows admin to create a new publisher")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> createPublisher(@RequestBody PublisherCreateDTO dto) {
        PublisherResponseDTO response = publisherService.createPublisher(dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_PUBLISHER, response));
    }

    @Operation(summary = "Update publisher", description = "Allows admin to update publisher information")
    @PutMapping("/{publisherId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> updatePublisher(
            @PathVariable Long publisherId,
            @RequestBody PublisherUpdateDTO dto) {
        PublisherResponseDTO response = publisherService.updatePublisher(publisherId, dto);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_PUBLISHER, response));
    }

    @Operation(summary = "Find publisher by ID", description = "Retrieve details of a specific publisher")
    @GetMapping("/{publisherId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getPublisherById(@PathVariable Long publisherId) {
        PublisherResponseDTO response = publisherService.findById(publisherId);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_PUBLISHER, response));
    }

    @Operation(summary = "Get all publishers", description = "Retrieve a paginated list of all publishers")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getAllPublishers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PublisherResponseDTO> response = publisherService.findAll(page, size);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_PUBLISHERS, response));
    }

    @Operation(summary = "Delete publisher", description = "Allows admin to delete a publisher by ID")
    @DeleteMapping("/{publisherId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> deletePublisher(@PathVariable Long publisherId) {
        publisherService.deletePublisher(publisherId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_PUBLISHER));
    }
    
    @Operation(summary = "Search publishers by name", description = "Find publishers that contain a given name")
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> searchPublisherByName(@RequestParam String title) {
        List<PublisherResponseDTO> response = publisherService.searchPublisherByName(title);
        return ResponseEntity.ok(new BasicResponse(Messages.SEARCH_PUBLISHERS, response));
    }
}
