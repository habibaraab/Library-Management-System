package com.learn.library_management.dto;

import com.learn.library_management.config.HasId;

import lombok.Data;

@Data
public class RoleResponseDTO implements HasId{

    private Long roleId;
    private String name;
    private String description;
    
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return roleId;
	}
}
