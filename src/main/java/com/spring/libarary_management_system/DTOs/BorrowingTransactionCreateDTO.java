package com.spring.libarary_management_system.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data
public class BorrowingTransactionCreateDTO {

    @NotNull(message = "Book is required")
    private Long bookId;

    @NotNull(message = "Member is required")
    private Long memberId;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
}
