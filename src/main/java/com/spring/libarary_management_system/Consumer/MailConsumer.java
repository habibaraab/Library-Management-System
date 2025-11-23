package com.spring.libarary_management_system.Consumer;

import com.spring.libarary_management_system.Config.RabbitConstants;
import com.spring.libarary_management_system.Events.PasswordCodeRegeneratedEvent;
import com.spring.libarary_management_system.Events.PasswordResetRequestedEvent;
import com.spring.libarary_management_system.Exception.MailSendingException;
import com.spring.libarary_management_system.Exception.Messages;
import com.spring.libarary_management_system.Service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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



}
