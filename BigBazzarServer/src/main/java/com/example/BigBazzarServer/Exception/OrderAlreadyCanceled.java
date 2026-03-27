package com.example.BigBazzarServer.Exception;

public class OrderAlreadyCanceled extends RuntimeException {
    public OrderAlreadyCanceled(String message) {
        super(message);
    }
}
