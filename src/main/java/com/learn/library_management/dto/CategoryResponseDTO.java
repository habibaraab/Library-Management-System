package com.learn.library_management.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.learn.library_management.config.HasId;

@Data
public class CategoryResponseDTO implements HasId{

    private Long categoryId;

    private String name;

    private String description;

    private String imageUrl;

    private Long parentId;

    private List<Long> subCategoryIds; 
    private List<String> subCategoryNames;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return categoryId;
	}
}
