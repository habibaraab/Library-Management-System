package com.learn.library_management.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingTransactionCreatedEvent {
    private Long transactionId;
    private Long memberId;
    private String memberName;
    private String memberEmail;
    private Long bookId;
    private String bookTitle;
    private LocalDate dueDate;
}
