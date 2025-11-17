package com.spring.libarary_management_system.Exception;



public class UsernameAlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistsException() {
        super(Messages.USERNAME_ALREADY_EXISTS);
    }

}