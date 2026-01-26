package com.example.BigBazzarServer.Exception;

public class CustomerUsernameAlreadyExisits extends RuntimeException {
    public CustomerUsernameAlreadyExisits(String message) {
        super(message);
    }
}
