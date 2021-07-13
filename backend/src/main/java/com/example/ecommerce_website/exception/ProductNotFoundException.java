package com.example.ecommerce_website.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Could not find any product with id = " + id + ".");
    }
    public ProductNotFoundException(String name) { super("Could not find any product with name = " + name + "."); }
}