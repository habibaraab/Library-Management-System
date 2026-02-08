package com.learn.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class CategoryCreateDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;
    
    private Long parentId;
}
