package com.spring.libarary_management_system.DTOs;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "Username Or Email is required")
    private String usernameOrEmail;

    @NotBlank(message = "password is required")
    private String password;
}