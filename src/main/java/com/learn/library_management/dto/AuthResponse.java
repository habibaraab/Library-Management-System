package com.learn.library_management.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class AuthResponse {
	
    private String accessToken;
    private String refreshToken;
}