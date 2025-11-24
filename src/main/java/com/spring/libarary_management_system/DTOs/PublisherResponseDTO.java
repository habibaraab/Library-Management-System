package com.spring.libarary_management_system.DTOs;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class PublisherResponseDTO{

    private Long publisherId;

    private String name;

    private String address;

    private String phone;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<BookInfo> bookIds;


}
