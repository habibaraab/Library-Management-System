package com.learn.library_management.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.*;
import com.learn.library_management.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Category Controller", description = "API for managing product categories and subcategories.")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create a new category", description = "Create a new category with optional image. Only admins can access this endpoint.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicResponse> createCategory(
            @Valid @RequestPart("category") CategoryCreateDTO categoryDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        CategoryResponseDTO createdCategory = categoryService.createCategory(categoryDTO, image);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_CATEGORY, createdCategory));
    }

    @Operation(summary = "Update a category", description = "Update an existing category by ID with optional image. Only admins can access this endpoint.")
    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicResponse> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestPart("category") CategoryUpdateDTO categoryDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        CategoryResponseDTO updateCategory = categoryService.updateCategory(categoryId, categoryDTO, image);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_CATEGORY, updateCategory));
    }

    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories. Requires authentication.")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CategoryResponseDTO> response = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_CATEGORIES,response));
    }

    @Operation(summary = "Get root categories", description = "Retrieve categories that do not have a parent. Requires authentication.")
    @GetMapping("/root")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getRootCategories() {
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_ROOT_CATEGORIES, categoryService.getRootCategories()));
    }

    @Operation(summary = "Get category by ID", description = "Retrieve a specific category by its ID. Requires authentication.")
    @GetMapping("/{categoryId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_CATEGORY, categoryService.getCategoryById(categoryId)));
    }

    @Operation(summary = "Get subcategories", description = "Retrieve all subcategories of a given parent category. Requires authentication.")
    @GetMapping("/{parentId}/subcategories")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getSubcategories(@PathVariable Long parentId) {
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_SUBCATEGORIES, categoryService.getSubcategories(parentId)));
    }

    @Operation(summary = "Delete a category", description = "Delete a specific category by ID. Only admins can access this endpoint.")
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicResponse> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_CATEGORY));
    }

    @Operation(summary = "Search categories by name", description = "Find categories that contain a given name. Requires authentication.")
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> searchCategoriesByName(@RequestParam String title) {
        List<CategoryResponseDTO> response = categoryService.searchCategoriesByName(title);
        return ResponseEntity.ok(new BasicResponse(Messages.SEARCH_USER, response));
    }

}
