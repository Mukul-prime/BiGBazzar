package com.example.BigBazzarServer.Exception;

public class SellerEmailAlreadyExist extends RuntimeException {
    public SellerEmailAlreadyExist(String message) {
        super(message);
    }
}
