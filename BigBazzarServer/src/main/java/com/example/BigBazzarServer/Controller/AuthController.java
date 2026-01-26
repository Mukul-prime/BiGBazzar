package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.LoginDTO;

import com.example.BigBazzarServer.DTO.Response.LoginResponseDTO;

import com.example.BigBazzarServer.Service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/seller/login")
    public ResponseEntity<LoginResponseDTO> sellerLogin(
            @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.sellerLogin(dto));
    }

    @PostMapping("/customer/login")
    public ResponseEntity<String> customerLogin(
            @RequestBody LoginDTO dto) {
        String token  =  authService.customerLogin(dto);

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false) // true in HTTPS
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .sameSite("Strict")
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Login successful");

    }
}
