package com.example.orderservice.orderservice.exception;

// Custom exception class
public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(String message) {
        super(message);
    }
}
