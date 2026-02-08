package com.learn.library_management.service;

import com.learn.library_management.events.DueDateReminderEvent;
import com.learn.library_management.events.OverdueNotificationEvent;
import com.learn.library_management.entities.BorrowingTransaction;
import com.learn.library_management.rabbitconfig.RabbitConstants;
import com.learn.library_management.repository.BorrowingTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowingNotificationScheduler {

	private final BorrowingTransactionRepository transactionRepository;
	private final RabbitTemplate rabbitTemplate;

	@Scheduled(cron = "0 0 9 * * ?")
	@Transactional
	public void sendDueRemindersAndOverdues() {
		LocalDate today = LocalDate.now();

		List<BorrowingTransaction> dueTomorrow = transactionRepository
				.findByStatus(BorrowingTransaction.Status.Borrowed).stream()
				.filter(tx -> tx.getDueDate().equals(today.plusDays(1))).collect(Collectors.toList());

		dueTomorrow.forEach(tx -> {
			DueDateReminderEvent e = new DueDateReminderEvent(tx.getTransactionId(), tx.getMember().getName(),
					tx.getMember().getEmail(), tx.getBook().getTitle(), tx.getDueDate());
			rabbitTemplate.convertAndSend(RabbitConstants.MAIL_EXCHANGE, RabbitConstants.MAIL_DUE_REMINDER_KEY, e);
		});

		List<BorrowingTransaction> overdue = transactionRepository.findByStatus(BorrowingTransaction.Status.Borrowed)
				.stream().filter(tx -> tx.getDueDate().isBefore(today)).collect(Collectors.toList());

		overdue.forEach(tx -> {
			long daysOverdue = ChronoUnit.DAYS.between(tx.getDueDate(), today);
			OverdueNotificationEvent e = new OverdueNotificationEvent(tx.getTransactionId(), tx.getMember().getName(),
					tx.getMember().getEmail(), tx.getBook().getTitle(), tx.getDueDate(), daysOverdue);
			rabbitTemplate.convertAndSend(RabbitConstants.MAIL_EXCHANGE, RabbitConstants.MAIL_OVERDUE_KEY, e);
		});
	}
}
