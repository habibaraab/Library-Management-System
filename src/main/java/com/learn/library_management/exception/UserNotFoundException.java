package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super(Messages.USER_NOT_FOUND);
	}
}