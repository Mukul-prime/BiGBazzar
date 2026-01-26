package com.example.BigBazzarServer.Custome_methods;

public class OTP_GENERATOR {
    public static String generateOTP() {
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }
}
