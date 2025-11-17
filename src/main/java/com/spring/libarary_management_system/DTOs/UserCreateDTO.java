package com.spring.libarary_management_system.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class UserCreateDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one digit, one lowercase, one uppercase letter, and one special character"
    )
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^(\\+201|01)[0125][0-9]{8}$", message = "Invalid Egyptian phone number format.")
    @Size(min = 11, max = 13)
    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Role id is required")
    private Long roleId;
}
