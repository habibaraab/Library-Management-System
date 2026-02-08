package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class RoleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RoleNotFoundException() {
        super(Messages.ROLE_NOT_FOUND);
    }
}
