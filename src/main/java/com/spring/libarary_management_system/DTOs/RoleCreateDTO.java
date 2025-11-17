package com.spring.libarary_management_system.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class RoleCreateDTO {

    @NotBlank(message = "Role name is required")
    private String name;


}
