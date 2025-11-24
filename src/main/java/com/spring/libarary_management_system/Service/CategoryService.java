package com.spring.libarary_management_system.Service;


import com.spring.libarary_management_system.DTOs.CategoryCreateDTO;
import com.spring.libarary_management_system.DTOs.CategoryResponseDTO;
import com.spring.libarary_management_system.DTOs.CategoryUpdateDTO;
import com.spring.libarary_management_system.Entity.Category;
import com.spring.libarary_management_system.Exception.CategoryNotFoundException;
import com.spring.libarary_management_system.Exception.Messages;
import com.spring.libarary_management_system.Exception.ParentCategoryNotFoundException;
import com.spring.libarary_management_system.Mapper.CategoryMapper;
import com.spring.libarary_management_system.Repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ImageService imageService;

    @Transactional
    @CachePut(value = "categories", key = "#result.categoryId")
    public CategoryResponseDTO createCategory(CategoryCreateDTO categoryDTO, MultipartFile image) throws Exception {
        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            throw new IllegalArgumentException(Messages.CATEGORY_ALREADY_EXISTS);
        }
        Category category = categoryMapper.toEntity(categoryDTO);

        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = imageService.uploadImage(image);
                category.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new Exception(Messages.UPLOAD_IMAGE_FAILED + e.getMessage());
            }
        }

        if (categoryDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new ParentCategoryNotFoundException());
            category.setParent(parentCategory);
        }
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDTO(savedCategory);
    }

    @Transactional
    @CachePut(value = "categories", key = "#categoryId")
    public CategoryResponseDTO updateCategory(Long categoryId, CategoryUpdateDTO categoryDTO, MultipartFile image)
            throws Exception {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());

        Optional<Category> optionalCategory = categoryRepository.findByName(categoryDTO.getName());

        if (optionalCategory.isPresent() && !optionalCategory.get().getCategoryId().equals(categoryId)) {
            throw new IllegalArgumentException(Messages.CATEGORY_ALREADY_EXISTS);
        }

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setDescription(categoryDTO.getDescription());

        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = imageService.uploadImage(image);
                existingCategory.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new Exception(Messages.UPLOAD_IMAGE_FAILED + e.getMessage());
            }
        }

        if (categoryDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new ParentCategoryNotFoundException());
            existingCategory.setParent(parentCategory);
        } else {
            existingCategory.setParent(null);
        }

        Category savedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(savedCategory);
    }

    @Transactional
    @CacheEvict(value = "categories", key = "#categoryId")
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException());

        if (!category.getBooks().isEmpty()) {
            throw new IllegalStateException(Messages.CANNOT_DELETE_CATEGORY_WITH_BOOKS);
        }
        if (!category.getSubCategories().isEmpty()) {
            throw new IllegalStateException(Messages.CANNOT_DELETE_CATEGORY_WITH_SUBCATEGORIES);
        }
        if (category.getParent() != null) {
            Category parent = category.getParent();
            parent.getSubCategories().remove(category);
            categoryRepository.save(parent);
        }

        categoryRepository.delete(category);
    }

    @Cacheable(value = "categories", key = "'root'")
    public List<CategoryResponseDTO> getRootCategories() {
        return categoryRepository.findByParentIsNull().stream().map(categoryMapper::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "categories", key = "'all_page_' + #page + '_' + #size")
    public Page<CategoryResponseDTO> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::toDTO);
    }

    @Cacheable(value = "categories", key = "'sub_' + #parentId")
    public List<CategoryResponseDTO> getSubcategories(Long parentId) {
        Category category = categoryRepository.findById(parentId)
                .orElseThrow(() -> new ParentCategoryNotFoundException());
        return categoryRepository.findByParent_CategoryId(category.getCategoryId()).stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "categories", key = "#categoryId")
    public CategoryResponseDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());
        return categoryMapper.toDTO(category);
    }

    @Cacheable(value = "categories", key = "'search_' + #title")
    public List<CategoryResponseDTO> searchCategoriesByName(String title) {
        return categoryRepository.findByNameContainingIgnoreCase(title).stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
