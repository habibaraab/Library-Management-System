package com.spring.libarary_management_system.Exception;

public class RoleAlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public RoleAlreadyExistsException() {
        super(Messages.ROLE_ALREADY_EXISTS);
    }

}