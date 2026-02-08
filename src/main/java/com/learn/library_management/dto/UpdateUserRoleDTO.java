package com.learn.library_management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRoleDTO {

	@NotNull(message = "Role Id is required")
	private Long roleId;
}
