package com.spring.libarary_management_system.Exception;

public class ParentCategoryNotFoundException extends RuntimeException  {
    private static final long serialVersionUID = 1L;

    public ParentCategoryNotFoundException() {
        super(Messages.PARENT_CATEGORY_NOT_FOUND);
    }
}
