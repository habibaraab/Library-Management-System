package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class PublisherNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PublisherNotFoundException() {
        super(Messages.PUBLISHER_NOT_FOUND);
    }
}
