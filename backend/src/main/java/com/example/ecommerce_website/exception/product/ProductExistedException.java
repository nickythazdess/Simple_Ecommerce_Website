package com.example.ecommerce_website.exception.product;

public class ProductExistedException extends RuntimeException {
    public ProductExistedException(String name) { super("Product " + name + " is already existed!"); }
}
