package com.example.BigBazzarServer.Exception;

public class CustomerAlreadyExist extends RuntimeException {
    public CustomerAlreadyExist(String message) {
        super(message);
    }
}
