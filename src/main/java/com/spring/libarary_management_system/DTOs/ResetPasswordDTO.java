package com.spring.libarary_management_system.DTOs;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResetPasswordDTO {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Code is required")
    private String code;

    @NotNull(message = "Enter a new password!")
    @NotBlank
    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Password must contain at least one letter and one number")
    private String newPassword;
}
