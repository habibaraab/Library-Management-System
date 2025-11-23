package com.spring.libarary_management_system.Exception;


public class InvalidResetCodeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidResetCodeException() {
        super(Messages.INVALID_RESET_CODE);
    }
}
