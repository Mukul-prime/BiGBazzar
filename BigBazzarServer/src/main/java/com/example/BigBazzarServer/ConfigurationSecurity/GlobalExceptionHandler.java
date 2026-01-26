package com.example.BigBazzarServer.ConfigurationSecurity;




import com.example.BigBazzarServer.Exception.CustomerAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExist.class)
    public ResponseEntity<Map<String, String>> handleCustomerAlreadyExist(
            CustomerAlreadyExist ex) {

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "fail");
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)  // 409
                .body(errorResponse);
    }

    // Optional: generic exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
