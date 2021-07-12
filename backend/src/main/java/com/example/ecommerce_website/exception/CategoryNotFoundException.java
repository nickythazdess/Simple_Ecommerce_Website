package com.example.ecommerce_website.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Could not find any category with id = " + id);
    }

}