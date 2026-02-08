package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class CategoryNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException() {
		super(Messages.CATEGORY_NOT_FOUND);
	}
	
}