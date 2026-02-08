package com.learn.library_management.events;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordCodeRegeneratedEvent {
    private String email;
    private String username;
    private String code;
}