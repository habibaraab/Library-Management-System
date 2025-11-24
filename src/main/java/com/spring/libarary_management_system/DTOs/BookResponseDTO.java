package com.spring.libarary_management_system.DTOs;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class BookResponseDTO{

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
    

}
