package com.spring.libarary_management_system.DTOs;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String roleName;


}
