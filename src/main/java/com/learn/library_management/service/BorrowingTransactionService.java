package com.learn.library_management.service;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BorrowingTransactionCreateDTO;
import com.learn.library_management.dto.BorrowingTransactionResponseDTO;
import com.learn.library_management.entities.*;
import com.learn.library_management.events.BorrowingTransactionCreatedEvent;
import com.learn.library_management.exception.*;
import com.learn.library_management.mapper.BorrowingTransactionMapper;
import com.learn.library_management.rabbitconfig.RabbitConstants;
import com.learn.library_management.repository.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class BorrowingTransactionService {

	private final BorrowingTransactionRepository transactionRepository;
	private final BookRepository bookRepository;
	private final MemberRepository memberRepository;
	private final UserRepository userRepository;
	private final BorrowingTransactionMapper mapper;
	private final RabbitTemplate rabbitTemplate;

	@Transactional
	@CachePut(value = "transactions", key = "#result.transactionId")
	public BorrowingTransactionResponseDTO createTransaction(@Valid BorrowingTransactionCreateDTO dto,
			Long handledByUserId) {
		Book book = bookRepository.findById(dto.getBookId()).orElseThrow(BookNotFoundException::new);

		Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(MemberNotFoundException::new);

		User handledBy = userRepository.findById(handledByUserId).orElseThrow(UserNotFoundException::new);

		BorrowingTransaction transaction = mapper.toEntity(dto);
		transaction.setBook(book);
		transaction.setMember(member);
		transaction.setHandledBy(handledBy);
		transaction.setDueDate(dto.getDueDate());
		transaction.setStatus(BorrowingTransaction.Status.Borrowed);

		BorrowingTransaction saved = transactionRepository.save(transaction);

		BorrowingTransactionCreatedEvent event = new BorrowingTransactionCreatedEvent(saved.getTransactionId(),
				member.getMemberId(), member.getName(), member.getEmail(), book.getBookId(), book.getTitle(),
				saved.getDueDate());

		rabbitTemplate.convertAndSend(RabbitConstants.MAIL_EXCHANGE, RabbitConstants.MAIL_BORROW_KEY, event);

		return mapper.toDTO(saved);
	}

	@Transactional
	@CachePut(value = "transactions", key = "#transactionId")
	public BorrowingTransactionResponseDTO updateStatusToReturned(Long transactionId) {
		BorrowingTransaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new RuntimeException(Messages.TRANSACTION_NOT_FOUND));

		transaction.setStatus(BorrowingTransaction.Status.Returned);
		transaction.setReturnedAt(java.time.LocalDateTime.now());

		BorrowingTransaction updated = transactionRepository.save(transaction);
		return mapper.toDTO(updated);
	}

	@Transactional
	@Cacheable(value = "transactions", key = "#transactionId")
	public BorrowingTransactionResponseDTO getById(Long transactionId) {
		BorrowingTransaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new RuntimeException(Messages.TRANSACTION_NOT_FOUND));
		return mapper.toDTO(transaction);
	}

	@Transactional
	@CacheEvict(value = "transactions", allEntries = true)
	public List<BorrowingTransactionResponseDTO> getAll() {
	    return transactionRepository.findAll().stream()
	            .map(mapper::toDTO)
	            .collect(Collectors.toList());
	}

	@Transactional
	@Cacheable(value = "transactions", key = "'member_' + #memberId")
	public List<BorrowingTransactionResponseDTO> getByMemberId(Long memberId) {
		memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		return transactionRepository.findByMember_MemberId(memberId).stream().map(mapper::toDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	@Cacheable(value = "transactions", key = "'book_' + #bookId")
	public List<BorrowingTransactionResponseDTO> getByBookId(Long bookId) {
		bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
		return transactionRepository.findByBook_BookId(bookId).stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@Transactional
	@Cacheable(value = "transactions", key = "'status_' + #status")
	public List<BorrowingTransactionResponseDTO> getByStatus(BorrowingTransaction.Status status) {
		return transactionRepository.findByStatus(status).stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void markOverdueTransactions() {
		List<BorrowingTransaction> borrowed = transactionRepository.findByStatus(BorrowingTransaction.Status.Borrowed);

		LocalDate today = LocalDate.now();
		borrowed.stream().filter(tx -> tx.getDueDate().isBefore(today))
				.forEach(tx -> tx.setStatus(BorrowingTransaction.Status.Overdue));

		transactionRepository.saveAll(borrowed);
	}

	@Transactional
	@CacheEvict(value = "transactions", key = "#transactionId")
	public void deleteTransaction(Long transactionId) {
		BorrowingTransaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new RuntimeException(Messages.TRANSACTION_NOT_FOUND));
		transactionRepository.delete(transaction);
	}
}