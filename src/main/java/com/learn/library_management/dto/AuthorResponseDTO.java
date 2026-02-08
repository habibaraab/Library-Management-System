package com.learn.library_management.dto;

import lombok.*;
import java.time.LocalDateTime;

import com.learn.library_management.config.HasId;

@Data
public class AuthorResponseDTO implements HasId{
    private Long authorId;
    private String name;
    private String bio;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	
    @Override
	public Long getId() {
		return authorId;
	}
}
