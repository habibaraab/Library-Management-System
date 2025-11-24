package com.spring.libarary_management_system.DTOs;

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
