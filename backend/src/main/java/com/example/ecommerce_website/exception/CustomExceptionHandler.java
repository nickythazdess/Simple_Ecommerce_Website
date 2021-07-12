package com.example.ecommerce_website.exception;

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

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ResponseEntity<String> categoryNotFoundException(CategoryNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundException(ProductNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorJSON(status, ex), status);
    }
}
