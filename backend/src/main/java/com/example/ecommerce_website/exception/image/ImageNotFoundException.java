package com.example.ecommerce_website.exception.image;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException() { super("Could not find any image."); }
}
