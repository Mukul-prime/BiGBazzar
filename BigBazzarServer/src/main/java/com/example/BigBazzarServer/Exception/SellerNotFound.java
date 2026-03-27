package com.example.BigBazzarServer.Exception;

public class SellerNotFound extends RuntimeException {
    public SellerNotFound(String message) {
        super(message);
    }
}
