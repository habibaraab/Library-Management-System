package com.learn.library_management.repository;

import com.learn.library_management.entities.BorrowingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByMember_MemberId(Long memberId);
    List<BorrowingTransaction> findByBook_BookId(Long bookId);
    List<BorrowingTransaction> findByStatus(BorrowingTransaction.Status status);
    boolean existsByBook_BookId(Long bookId);

}
