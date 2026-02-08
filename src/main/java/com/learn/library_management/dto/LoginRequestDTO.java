package com.learn.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "Username Or Email is required")
	private String usernameOrEmail;
    
    @NotBlank(message = "password is required")
    private String password;
}