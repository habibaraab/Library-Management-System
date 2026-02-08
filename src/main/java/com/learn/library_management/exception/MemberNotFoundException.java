package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class MemberNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MemberNotFoundException() {
        super(Messages.MEMBER_NOT_FOUND);
    }
}
