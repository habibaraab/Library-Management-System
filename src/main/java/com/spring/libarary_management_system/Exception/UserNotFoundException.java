package com.spring.libarary_management_system.Exception;


public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super(Messages.USER_NOT_FOUND);
    }
}