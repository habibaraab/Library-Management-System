package com.learn.library_management.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.learn.library_management.config.Messages;
import com.learn.library_management.events.BorrowingTransactionCreatedEvent;
import com.learn.library_management.events.DueDateReminderEvent;
import com.learn.library_management.events.OverdueNotificationEvent;
import com.learn.library_management.events.PasswordCodeRegeneratedEvent;
import com.learn.library_management.events.PasswordResetRequestedEvent;
import com.learn.library_management.events.WelcomeEmailEvent;
import com.learn.library_management.exception.MailSendingException;
import com.learn.library_management.rabbitconfig.RabbitConstants;
import com.learn.library_management.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailConsumer {
	
	private final EmailService emailService;

    @RabbitListener(queues = RabbitConstants.MAIL_RESET_QUEUE)
    public void handlePasswordReset(PasswordResetRequestedEvent event) {
        System.out.println("Received PasswordResetRequestedEvent for user: " + event.getEmail());

        try {
            emailService.sendCode(event, Messages.RESET_PASSWORD);
        } catch (Exception e) {
            throw new MailSendingException();
        }
    }
    
	@RabbitListener(queues = RabbitConstants.MAIL_CODE_QUEUE)
	public void handlePasswordCodeRegenerated(PasswordCodeRegeneratedEvent event) {
		System.out.println("Received PasswordCodeRegeneratedEvent for user: " + event.getEmail());

		try {
			emailService.sendRegenerateCode(event, Messages.RESEND_CODE);
		} catch (Exception e) {
			throw new MailSendingException();
		}
	}
	
	@RabbitListener(queues = RabbitConstants.WELCOME_QUEUE)
	public void handleWelcomeEmail(WelcomeEmailEvent event) {
	    System.out.println("Received WelcomeEmailEvent for user: " + event.getEmail());

	    try {
	        emailService.sendWelcomeEmail(event);
	    } catch (Exception e) {
	        throw new MailSendingException();
	    }
	}

	@RabbitListener(queues = RabbitConstants.MAIL_BORROW_QUEUE)
	public void handleBorrowingEmail(BorrowingTransactionCreatedEvent event) {
	    System.out.println("Received BorrowingTransactionCreatedEvent for user: " + event.getMemberEmail());
	    try {
	        emailService.sendBorrowingConfirmation(event);
	    } catch (Exception e) {
	        throw new MailSendingException();
	    }
	}

	@RabbitListener(queues = RabbitConstants.MAIL_DUE_REMINDER_QUEUE)
	public void handleDueReminder(DueDateReminderEvent event) {
	    System.out.println("Received DueDateReminderEvent for user: " + event.getMemberEmail());
	    try {
	        emailService.sendDueDateReminder(event);
	    } catch (Exception e) {
	        throw new MailSendingException();
	    }
	}

	@RabbitListener(queues = RabbitConstants.MAIL_OVERDUE_QUEUE)
	public void handleOverdue(OverdueNotificationEvent event) {
	    System.out.println("Received OverdueNotificationEvent for user: " + event.getMemberEmail());
	    try {
	        emailService.sendOverdueNotification(event);
	    } catch (Exception e) {
	        throw new MailSendingException();
	    }
	}

}
