package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class RoleAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RoleAlreadyExistsException() {
		super(Messages.ROLE_ALREADY_EXISTS);
	}
	
}