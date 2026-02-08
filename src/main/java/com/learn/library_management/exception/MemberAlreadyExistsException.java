package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class MemberAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MemberAlreadyExistsException() {
		super(Messages.MEMBER_ALREADY_EXISTS);
	}
	
}