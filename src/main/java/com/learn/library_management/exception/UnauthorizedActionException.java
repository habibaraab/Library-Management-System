package com.learn.library_management.exception;

public class UnauthorizedActionException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UnauthorizedActionException(String message) {
		super(message);
	}
}