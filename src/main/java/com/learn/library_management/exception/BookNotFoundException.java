package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class BookNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BookNotFoundException() {
        super(Messages.BOOK_NOT_FOUND);
    }
}
