package com.example.BigBazzarServer.Exception;

public class CustomerNotCreated extends RuntimeException {
    public CustomerNotCreated(String message) {
        super(message);
    }
}
