package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class UsernameAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UsernameAlreadyExistsException() {
		super(Messages.USERNAME_ALREADY_EXISTS);
	}
	
}