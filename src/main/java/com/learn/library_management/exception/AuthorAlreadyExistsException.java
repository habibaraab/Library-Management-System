package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class AuthorAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AuthorAlreadyExistsException() {
		super(Messages.AUTHOR_ALREADY_EXISTS);
	}
	
}