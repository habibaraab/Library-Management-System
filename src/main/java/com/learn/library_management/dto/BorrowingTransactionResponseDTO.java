package com.learn.library_management.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.learn.library_management.config.HasId;
import com.learn.library_management.entities.BorrowingTransaction;

@Data
public class BorrowingTransactionResponseDTO implements HasId{

    private Long transactionId;
    private Long bookId;
    private String bookTitle;
    private Long memberId;
    private String memberName;
    private LocalDateTime borrowedAt;
    private LocalDate dueDate;
    private LocalDateTime returnedAt;
    private BorrowingTransaction.Status status;
    private Long handledById;
    private String handledByUsername;
    private Double fineAmount;
    
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return transactionId;
	}
}
