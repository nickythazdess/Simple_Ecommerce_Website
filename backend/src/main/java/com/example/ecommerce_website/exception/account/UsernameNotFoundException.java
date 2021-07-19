package com.example.ecommerce_website.exception.account;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String name) {
        super("Could not find any account with username = " + name + ".");
    }
}
