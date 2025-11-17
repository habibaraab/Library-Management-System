package com.spring.libarary_management_system.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
}