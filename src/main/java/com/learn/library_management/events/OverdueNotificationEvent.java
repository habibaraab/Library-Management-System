package com.learn.library_management.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverdueNotificationEvent {
    private Long transactionId;
    private String memberName;
    private String memberEmail;
    private String bookTitle;
    private LocalDate dueDate;
    private long daysOverdue;
}
