package com.example.BigBazzarServer.Exception;

public class CustomerNumberExist extends RuntimeException {
    public CustomerNumberExist(String message) {
        super(message);
    }
}
