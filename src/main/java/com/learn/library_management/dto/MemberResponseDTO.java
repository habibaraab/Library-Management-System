package com.learn.library_management.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.learn.library_management.config.HasId;

@Data
public class MemberResponseDTO implements HasId{

    private Long memberId;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Long> transactionIds;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return memberId;
	}
}
