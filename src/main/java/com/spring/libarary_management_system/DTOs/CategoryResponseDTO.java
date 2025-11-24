package com.spring.libarary_management_system.DTOs;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class CategoryResponseDTO{

    private Long categoryId;

    private String name;

    private String description;

    private String imageUrl;

    private Long parentId;

    private List<Long> subCategoryIds; 
    private List<String> subCategoryNames;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
