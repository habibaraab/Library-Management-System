package com.learn.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class RoleCreateDTO {

    @NotBlank(message = "Role name is required")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;
}
