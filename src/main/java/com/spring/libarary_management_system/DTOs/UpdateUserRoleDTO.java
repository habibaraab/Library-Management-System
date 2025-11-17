package com.spring.libarary_management_system.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRoleDTO {

    @NotNull(message = "Role Id is required")
    private Long roleId;
}
