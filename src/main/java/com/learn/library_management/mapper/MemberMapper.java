package com.learn.library_management.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.*;

import com.learn.library_management.entities.BorrowingTransaction;
import com.learn.library_management.entities.Member;
import com.learn.library_management.dto.MemberCreateDTO;
import com.learn.library_management.dto.MemberResponseDTO;

@Mapper(componentModel = "spring")
public interface MemberMapper {

	@Mapping(target = "transactionIds", source = "transactions", qualifiedByName = "mapTransactionIds")
	MemberResponseDTO toDTO(Member member);

    @Mapping(target = "memberId", ignore = true)
    @Mapping(target = "status",  ignore = true) 
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    Member toEntity(MemberCreateDTO dto);
    
    @Named("mapTransactionIds")
    default List<Long> mapTransactionIds(List<BorrowingTransaction> transactions) {
        return transactions.stream()
                           .map(BorrowingTransaction::getTransactionId)
                           .collect(Collectors.toList());
    }

}
