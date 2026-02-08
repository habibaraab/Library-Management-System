package com.learn.library_management.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Data
public class BookUpdateDTO {

    @NotBlank(message = "Book title is required")
    private String title;

    @Size(max = 5000, message = "Summary must be less than 5000 characters")
    private String summary;

    private String language;

    @Min(value = 0, message = "Publication year cannot be negative")
    @Max(value = 2100, message = "Publication year cannot be greater than 2100")
    private Integer publicationYear;

    @Size(max = 20, message = "ISBN must be less than 20 characters")
    private String isbn;

    private String edition;

    @NotNull(message = "Publisher is required")
    private Long publisherId;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Authors list cannot be null")
    private List<Long> authorIds;
}