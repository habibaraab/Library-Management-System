package com.learn.library_management.controller;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BasicResponse;
import com.learn.library_management.dto.BorrowingTransactionCreateDTO;
import com.learn.library_management.dto.BorrowingTransactionResponseDTO;
import com.learn.library_management.entities.BorrowingTransaction;
import com.learn.library_management.entities.User;
import com.learn.library_management.service.BorrowingTransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Borrowing Transaction Controller", description = "API for managing borrowing transactions.")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BorrowingTransactionController {

    private final BorrowingTransactionService transactionService;

    @Operation(summary = "Create a new transaction", description = "Allows librarian/admin to create a new borrowing transaction")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> createTransaction(
            @RequestBody BorrowingTransactionCreateDTO dto,
            @AuthenticationPrincipal User user) {
    	Long handledByUserId = user.getId();
        BorrowingTransactionResponseDTO response = transactionService.createTransaction(dto, handledByUserId);
        return ResponseEntity.ok(new BasicResponse(Messages.BORROW_SUCCESS, response));
    }

    @Operation(summary = "Update status to Returned", description = "Mark a borrowing transaction as Returned")
    @PutMapping("/{transactionId}/return")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> returnBook(@PathVariable Long transactionId) {
        BorrowingTransactionResponseDTO response = transactionService.updateStatusToReturned(transactionId);
        return ResponseEntity.ok(new BasicResponse(Messages.RETURN_SUCCESS, response));
    }

    @Operation(summary = "Find transaction by ID", description = "Retrieve details of a specific borrowing transaction")
    @GetMapping("/{transactionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getTransactionById(@PathVariable Long transactionId) {
        BorrowingTransactionResponseDTO response = transactionService.getById(transactionId);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_TRANSACTION, response));
    }

    @Operation(summary = "Get all transactions", description = "Retrieve all borrowing transactions")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getAllTransactions() {
        List<BorrowingTransactionResponseDTO> response = transactionService.getAll();
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_TRANSACTIONS, response));
    }

    @Operation(summary = "Get transactions by Member", description = "Retrieve borrowing transactions for a specific member")
    @GetMapping("/member/{memberId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getByMember(@PathVariable Long memberId) {
        List<BorrowingTransactionResponseDTO> response = transactionService.getByMemberId(memberId);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_TRANSACTIONS, response));
    }

    @Operation(summary = "Get transactions by Book", description = "Retrieve borrowing transactions for a specific book")
    @GetMapping("/book/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getByBook(@PathVariable Long bookId) {
        List<BorrowingTransactionResponseDTO> response = transactionService.getByBookId(bookId);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_TRANSACTIONS, response));
    }

    @Operation(summary = "Get transactions by Status", description = "Retrieve borrowing transactions with a specific status")
    @GetMapping("/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getByStatus(@RequestParam BorrowingTransaction.Status status) {
        List<BorrowingTransactionResponseDTO> response = transactionService.getByStatus(status);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_TRANSACTIONS, response));
    }

    @Operation(summary = "Delete transaction", description = "Allows admin/librarian to delete a borrowing transaction by ID")
    @DeleteMapping("/{transactionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_TRANSACTION));
    }
}