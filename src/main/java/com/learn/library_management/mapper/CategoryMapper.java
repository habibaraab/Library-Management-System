package com.learn.library_management.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.learn.library_management.dto.CategoryCreateDTO;
import com.learn.library_management.dto.CategoryResponseDTO;
import com.learn.library_management.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.categoryId")
    @Mapping(target = "subCategoryIds", source = "subCategories", qualifiedByName = "subCategoryIds")
    @Mapping(target = "subCategoryNames", source = "subCategories", qualifiedByName = "subCategoryNames")
    CategoryResponseDTO toDTO(Category category);

    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    Category toEntity(CategoryCreateDTO dto);

    @Named("subCategoryIds")
    default List<Long> mapSubCategoryIds(List<Category> subCategories) {
        return subCategories.stream()
                            .map(Category::getCategoryId)
                            .collect(Collectors.toList());
    }

    @Named("subCategoryNames")
    default List<String> mapSubCategoryNames(List<Category> subCategories) {
        return subCategories.stream()
                            .map(Category::getName)
                            .collect(Collectors.toList());
    }
}