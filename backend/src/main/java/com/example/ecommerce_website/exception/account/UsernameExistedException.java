package com.example.ecommerce_website.exception.account;

public class UsernameExistedException extends RuntimeException {
    public UsernameExistedException(String name) { super("Username " + name + " is already taken!"); }
}
