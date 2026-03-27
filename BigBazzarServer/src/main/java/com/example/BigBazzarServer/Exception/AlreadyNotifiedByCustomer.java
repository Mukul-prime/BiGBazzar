package com.example.BigBazzarServer.Exception;

public class AlreadyNotifiedByCustomer extends RuntimeException {
    public AlreadyNotifiedByCustomer(String message) {
        super(message);
    }
}
