package com.example.ecommerce_website.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Could not find any product with id = " + id + ".");
    }
    public ProductNotFoundException(String category) {
        super("Could not find any product in " + category + "category.");
    }
}