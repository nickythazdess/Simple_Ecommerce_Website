package com.example.ecommerce_website.exception.account;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String name) {
        super("Could not find any account with email = " + name + ".");
    }
}
