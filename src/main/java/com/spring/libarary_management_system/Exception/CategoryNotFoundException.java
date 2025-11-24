package com.spring.libarary_management_system.Exception;


public class CategoryNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CategoryNotFoundException() {
        super(Messages.CATEGORY_NOT_FOUND);
    }

}