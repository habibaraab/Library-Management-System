package com.spring.libarary_management_system.Exception;

public class InvalidCurrentPasswordException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidCurrentPasswordException() {
        super(Messages.INVALID_PASSWORD);
    }
}