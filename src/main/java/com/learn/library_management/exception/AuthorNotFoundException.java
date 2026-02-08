package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class AuthorNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthorNotFoundException() {
        super(Messages.AUTHOR_NOT_FOUND);
    }
}
