package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class MailSendingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public MailSendingException() {
        super(Messages.FAILED_EMAIL);
    }
}