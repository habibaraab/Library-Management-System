package com.learn.library_management.entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "borrowing_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "borrowed_at")
    private LocalDateTime borrowedAt;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "handled_by", nullable = false)
    private User handledBy;

    @Column(name = "fine_amount")
    private Double fineAmount;

    public enum Status {
        Borrowed,
        Returned,
        Overdue
    }

    @PrePersist
    protected void onCreate() {
        this.borrowedAt = LocalDateTime.now();
        if (this.status == null) this.status = Status.Borrowed;
    }
}