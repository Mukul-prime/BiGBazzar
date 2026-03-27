package com.example.BigBazzarServer.Exception;

public class CityNotFound extends RuntimeException {
    public CityNotFound(String message) {
        super(message);
    }
}
