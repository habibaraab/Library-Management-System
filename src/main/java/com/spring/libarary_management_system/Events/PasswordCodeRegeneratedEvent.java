package com.spring.libarary_management_system.Events;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordCodeRegeneratedEvent {
    private String email;
    private String username;
    private String code;
}