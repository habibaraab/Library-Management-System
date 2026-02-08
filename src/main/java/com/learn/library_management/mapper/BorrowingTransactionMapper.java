package com.learn.library_management.mapper;

import com.learn.library_management.dto.BorrowingTransactionCreateDTO;
import com.learn.library_management.dto.BorrowingTransactionResponseDTO;
import com.learn.library_management.entities.BorrowingTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowingTransactionMapper {

    @Mapping(target = "bookId", source = "book.bookId")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "memberId", source = "member.memberId")
    @Mapping(target = "memberName", source = "member.name")
    @Mapping(target = "handledById", source = "handledBy.id")
    @Mapping(target = "handledByUsername", source = "handledBy.username")
    BorrowingTransactionResponseDTO toDTO(BorrowingTransaction transaction);
    
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "handledBy", ignore = true)
    @Mapping(target = "borrowedAt", ignore = true)
    @Mapping(target = "returnedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "fineAmount", ignore = true)
    BorrowingTransaction toEntity(BorrowingTransactionCreateDTO dto);
}
