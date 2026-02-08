package com.learn.library_management.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.learn.library_management.config.HasId;

@Data
public class BookResponseDTO implements HasId{

    private Long bookId;
    private String title;
    private String summary;
    private String language;
    private Integer publicationYear;
    private String isbn;
    private String edition;
    private String coverImage;
    private Long publisherId;
    private String publisherName;
    private Long categoryId;
    private String categoryName;
    private List<String> authorNames;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Override
	public Long getId() {
		return bookId;
	}
}
