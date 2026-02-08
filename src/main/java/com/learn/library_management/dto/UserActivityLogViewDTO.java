package com.learn.library_management.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
public class UserActivityLogViewDTO {

    private Long logId;
    private Long userId;
    private String username;
    private String action;
    private String entityType;
    private Long entityId;
    private LocalDateTime createdAt;
}
