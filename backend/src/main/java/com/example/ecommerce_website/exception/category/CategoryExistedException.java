package com.example.ecommerce_website.exception.category;

public class CategoryExistedException extends RuntimeException {
    public CategoryExistedException(String name) { super("Category " + name + " is already existed!"); }
}
