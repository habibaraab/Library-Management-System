package com.spring.libarary_management_system.Exception;


public class EmailAlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EmailAlreadyExistsException() {
        super(Messages.EMAIL_ALREADY_EXISTS);
    }

}