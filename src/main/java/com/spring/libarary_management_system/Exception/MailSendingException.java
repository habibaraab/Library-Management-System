package com.spring.libarary_management_system.Exception;

public class MailSendingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MailSendingException() {
        super(Messages.FAILED_EMAIL);
    }
}