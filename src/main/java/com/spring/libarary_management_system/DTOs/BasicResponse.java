package com.spring.libarary_management_system.DTOs;

import java.util.Date;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {

    private String message;
    private Object data;
    private Date timestamp = new Date();

    public BasicResponse(String message) {
        this.message = message;
    }

    public BasicResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}