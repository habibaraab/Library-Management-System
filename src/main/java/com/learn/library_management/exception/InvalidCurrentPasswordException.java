package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class InvalidCurrentPasswordException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public InvalidCurrentPasswordException() {
        super(Messages.INVALID_PASSWORD);
    }
}