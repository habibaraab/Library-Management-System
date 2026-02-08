package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class PublisherHasBooksException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PublisherHasBooksException() {
		super(Messages.PUBLISHER_HAS_BOOKS);
	}
	
}