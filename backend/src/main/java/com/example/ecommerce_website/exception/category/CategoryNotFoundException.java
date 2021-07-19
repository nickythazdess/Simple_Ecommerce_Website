package com.example.ecommerce_website.exception.category;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Could not find any category with id = " + id);
    }

    public CategoryNotFoundException(String name) {
        super("Could not find any category with name = " + name);
    }
}