package com.learn.library_management.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WelcomeEmailEvent {
    private String name;
    private String email;
    private String position;
}
