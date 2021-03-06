package com.example.ecommerce_website.exception.account;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) { super("Could not found any account with corresponding id!"); }
    public AccountNotFoundException(String name) { super("Could not find any account with name = " + name + "."); }
}
