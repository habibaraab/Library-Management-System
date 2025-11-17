package com.spring.libarary_management_system.Exception;

public class RoleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RoleNotFoundException() {
        super(Messages.ROLE_NOT_FOUND);
    }
}

