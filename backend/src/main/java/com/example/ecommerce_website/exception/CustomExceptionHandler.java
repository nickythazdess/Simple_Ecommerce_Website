package com.example.ecommerce_website.exception;

import com.example.ecommerce_website.exception.account.*;
import com.example.ecommerce_website.exception.category.CategoryExistedException;
import com.example.ecommerce_website.exception.category.CategoryNotFoundException;
import com.example.ecommerce_website.exception.product.ProductExistedException;
import com.example.ecommerce_website.exception.product.ProductNotFoundException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    private String prepareErrorJSON(HttpStatus status, Exception ex) {
        JSONObject errorJSON = new JSONObject();
        try {
            errorJSON.put("status", status.value());
            errorJSON.put("error", status.getReasonPhrase());
            errorJSON.put("message", ex.getMessage().split(":")[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return errorJSON.toString();
    }

    //Category Exception Handler

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ResponseEntity<String> categoryNotFoundException(CategoryNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }

    @ExceptionHandler(value = CategoryExistedException.class)
    public ResponseEntity<String> categoryExistedException(CategoryExistedException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }

    //Product Exception Handler

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundException(ProductNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }

    @ExceptionHandler(value = ProductExistedException.class)
    public ResponseEntity<String> productExistedException(ProductExistedException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }

    //Account Exception Handler

    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<String> accountNotFoundException(AccountNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundException(UsernameNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }

    @ExceptionHandler(value = EmailNotFoundException.class)
    public ResponseEntity<String> emailNotFoundException(EmailNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }

    @ExceptionHandler(value = UsernameExistedException.class)
    public ResponseEntity<String> usernameExistedException(UsernameExistedException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }

    @ExceptionHandler(value = EmailExistedException.class)
    public ResponseEntity<String> emailExistedException(EmailExistedException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }
}
