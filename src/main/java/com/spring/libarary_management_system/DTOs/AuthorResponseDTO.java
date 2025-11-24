package com.spring.libarary_management_system.DTOs;

import lombok.*;
import java.time.LocalDateTime;


@Data
public class AuthorResponseDTO  {
    private Long authorId;
    private String name;
    private String bio;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	

}
