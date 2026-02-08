package com.learn.library_management.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.learn.library_management.config.HasId;

@Data
public class PublisherResponseDTO implements HasId{

    private Long publisherId;

    private String name;

    private String address;

    private String phone;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<BookInfo> bookIds;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return publisherId;
	}
}
