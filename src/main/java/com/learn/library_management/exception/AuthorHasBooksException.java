package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class AuthorHasBooksException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AuthorHasBooksException() {
		super(Messages.AUTHOR_HAS_BOOKS);
	}
	
}