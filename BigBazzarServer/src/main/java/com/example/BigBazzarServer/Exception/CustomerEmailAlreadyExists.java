package com.example.BigBazzarServer.Exception;

public class CustomerEmailAlreadyExists extends RuntimeException {
    public CustomerEmailAlreadyExists(String message) {
        super(message);
    }
}
