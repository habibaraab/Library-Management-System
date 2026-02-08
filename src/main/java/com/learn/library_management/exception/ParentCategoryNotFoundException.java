package com.learn.library_management.exception;

import com.learn.library_management.config.Messages;

public class ParentCategoryNotFoundException extends RuntimeException  {
    private static final long serialVersionUID = 1L;

    public ParentCategoryNotFoundException() {
        super(Messages.PARENT_CATEGORY_NOT_FOUND);
    }
}
