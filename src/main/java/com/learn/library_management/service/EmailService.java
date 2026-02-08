package com.learn.library_management.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.learn.library_management.events.BorrowingTransactionCreatedEvent;
import com.learn.library_management.events.DueDateReminderEvent;
import com.learn.library_management.events.OverdueNotificationEvent;
import com.learn.library_management.events.PasswordCodeRegeneratedEvent;
import com.learn.library_management.events.PasswordResetRequestedEvent;
import com.learn.library_management.events.WelcomeEmailEvent;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;

	@Value("${spring.mail.username}")
	private String fromEmail;

	public void sendWelcomeEmail(WelcomeEmailEvent user) {
		sendEmail(user.getEmail(), "Welcome to Library Management System!", "emails/welcome",
				new ContextBuilder().add("username", user.getName()).add("position", user.getPosition()).build());
	}

	public void sendCode(PasswordResetRequestedEvent user, String subject) {
		sendEmail(user.getEmail(), subject, "emails/send-code",
				new ContextBuilder().add("name", user.getUsername()).add("code", user.getCode()).build());
	}

	public void sendRegenerateCode(PasswordCodeRegeneratedEvent user, String subject) {
		sendEmail(user.getEmail(), subject, "emails/send-code",
				new ContextBuilder().add("name", user.getUsername()).add("code", user.getCode()).build());
	}

	public void sendBorrowingConfirmation(BorrowingTransactionCreatedEvent e) {
		sendEmail(e.getMemberEmail(), "Book Borrowing Confirmation — Library System", "emails/borrow-confirmation",
				new ContextBuilder().add("username", e.getMemberName()).add("bookTitle", e.getBookTitle())
						.add("dueDate", e.getDueDate().toString()).build());
	}

	public void sendDueDateReminder(DueDateReminderEvent e) {
		sendEmail(e.getMemberEmail(), "Reminder: Your book is due tomorrow", "emails/due-reminder",
				new ContextBuilder().add("username", e.getMemberName()).add("bookTitle", e.getBookTitle())
						.add("dueDate", e.getDueDate().toString()).build());
	}

	public void sendOverdueNotification(OverdueNotificationEvent e) {
		sendEmail(e.getMemberEmail(), "Overdue Alert: Please return your book", "emails/overdue",
				new ContextBuilder().add("username", e.getMemberName()).add("bookTitle", e.getBookTitle())
						.add("dueDate", e.getDueDate().toString()).add("daysOverdue", e.getDaysOverdue()).build());
	}

	private void sendEmail(String to, String subject, String templatePath, Context context) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setSubject(subject);

			String htmlContent = templateEngine.process(templatePath, context);
			helper.setText(htmlContent, true);

			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to send email", e);
		}
	}

	// Nested helper class to make adding context variables easier
	private static class ContextBuilder {
		private final Context context = new Context();

		public ContextBuilder add(String key, Object value) {
			context.setVariable(key, value);
			return this;
		}

		public Context build() {
			return context;
		}
	}
}