package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class BookAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BookAlreadyExistsException() {
		super(Messages.BOOK_ALREADY_EXISTS);
	}
	
}