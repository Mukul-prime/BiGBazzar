package com.example.BigBazzarServer.Exception;

public class DoesNotHaveProduct extends RuntimeException {
    public DoesNotHaveProduct(String message) {
        super(message);
    }
}
