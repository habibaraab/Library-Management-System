package com.spring.libarary_management_system.Events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequestedEvent {
    private Long userId;
    private String username;
    private String email;
    private String code;
    private LocalDateTime requestedAt;
}