package com.learn.library_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Library Management API")
                .version("1.0")
                .description("Backend API for managing books, members, borrowing transactions, and user roles in the library system.")
                .contact(new Contact()
                    .name("Abdulrahman Ahmed")
                    .email("abdulrahman.ahmedd@gmail.com")
                )
            );
    }
}
