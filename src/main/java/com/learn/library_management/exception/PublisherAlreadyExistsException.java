package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class PublisherAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PublisherAlreadyExistsException() {
		super(Messages.PUBLISHER_ALREADY_EXISTS);
	}
	
}