package com.example.ecommerce_website.exception.account;

public class EmailExistedException extends RuntimeException {
    public EmailExistedException(String name) { super("Account with email " + name + " is already existed!"); }
}
