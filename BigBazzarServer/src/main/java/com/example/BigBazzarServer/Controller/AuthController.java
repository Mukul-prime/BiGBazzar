package com.example.BigBazzarServer.Controller;

import com.example.BigBazzarServer.DAO.DeleteCustomerDAO;
import com.example.BigBazzarServer.DTO.Request.LoginDTO;
import com.example.BigBazzarServer.DTO.Response.LoginResponseDTO;
import com.example.BigBazzarServer.Service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final DeleteCustomerDAO deleteCustomerDAO;


    private ResponseCookie generateCookie(String token) {
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();
    }

    @PostMapping("/seller/login")
    public ResponseEntity<LoginResponseDTO> sellerLogin(
            @RequestBody LoginDTO dto) {

        LoginResponseDTO response = authService.sellerLogin(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, generateCookie(response.getToken()).toString())
                .body(response);
    }

    @PostMapping(" ")
    public ResponseEntity<LoginResponseDTO> customerLogin(
            @RequestBody LoginDTO dto) {

        LoginResponseDTO response = authService.customerLogin(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, generateCookie(response.getToken()).toString())
                .body(response);
    }


    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponseDTO> adminLogin(
            @RequestBody LoginDTO dto) {

        LoginResponseDTO response = authService.adminLogin(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, generateCookie(response.getToken()).toString())
                .body(response);
    }
}