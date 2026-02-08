package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class InvalidResetCodeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public InvalidResetCodeException() {
        super(Messages.INVALID_RESET_CODE);
    }
}