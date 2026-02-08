package com.learn.library_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class AuthorUpdateDTO {

    @NotBlank(message = "Author name is required")
    private String name;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(max = 2000, message = "Bio must be less than 2000 characters")
    private String bio;
}
