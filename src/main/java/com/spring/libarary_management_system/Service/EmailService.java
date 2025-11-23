package com.spring.libarary_management_system.Service;


import com.spring.libarary_management_system.Events.PasswordCodeRegeneratedEvent;
import com.spring.libarary_management_system.Events.PasswordResetRequestedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;


    public void sendCode(PasswordResetRequestedEvent user, String subject) {
        sendEmail(user.getEmail(), subject, "emails/send-code",
                new ContextBuilder().add("name", user.getUsername()).add("code", user.getCode()).build());
    }

    public void sendRegenerateCode(PasswordCodeRegeneratedEvent user, String subject) {
        sendEmail(user.getEmail(), subject, "emails/send-code",
                new ContextBuilder().add("name", user.getUsername()).add("code", user.getCode()).build());
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