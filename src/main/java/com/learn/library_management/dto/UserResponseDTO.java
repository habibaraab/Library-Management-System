package com.learn.library_management.dto;

import com.learn.library_management.config.HasId;

import lombok.Data;

@Data
public class UserResponseDTO implements HasId{

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String image;
    private String roleName;
    
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
}
