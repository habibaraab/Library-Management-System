package com.spring.libarary_management_system.DTOs;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class MemberResponseDTO {

    private Long memberId;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Long> transactionIds;


}
