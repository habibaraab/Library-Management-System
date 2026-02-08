package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class TransactionNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException() {
        super(Messages.TRANSACTION_NOT_FOUND);
    }
}
