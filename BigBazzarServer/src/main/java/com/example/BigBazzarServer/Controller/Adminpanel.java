package com.example.BigBazzarServer.Controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.BigBazzarServer.DTO.Request.AdminRequest;
import com.example.BigBazzarServer.DTO.Request.UpdatePasswordRequest;
import com.example.BigBazzarServer.Exception.*;
import com.example.BigBazzarServer.Service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Admin")
@Slf4j
@RequiredArgsConstructor
public class Adminpanel {

    private final AdminService adminService;


    @GetMapping("/")
    public String Welcome(){
        return "Welcome to BigBazzarServer! Admins pannel";
    }

    @PostMapping("/Admin")
    public ResponseEntity<?> CreateAdmin(@RequestBody AdminRequest adminRequest){
        log.info("Creating Admin panel");
        try{
            String response = adminService.createAdmin(adminRequest);
            return new  ResponseEntity<>(response, HttpStatus.CREATED);

        }
        catch (EmailAlreadyExists | Useralreadycreated
               | CustomerEmailAlreadyExists | SellerEmailAlreadyExist e){
            return new ResponseEntity<>(  e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest, Authentication authentication){
        log.info("Updating password");
        try{
            String email =  authentication.getName();
            String response = adminService.UpdatePassword(updatePasswordRequest, email);
            return new ResponseEntity<>(response , HttpStatus.OK);
        }
        catch (AdminNotfound e){
            log.info("Admin not found");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> DeteAllcustomer(Authentication authentication){
        log.info("DeteAllcustomer");
        try{
            String email =  authentication.getName();
            String response = adminService.deletecustomer(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (AdminNotfound e){
            log.info("Admin not found");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Seller")
    public ResponseEntity<?> DeleteSeller(Authentication authentication){
        log.info("Deleting Seller");
        try{
            String email =  authentication.getName();
            String response = adminService.deleteseller(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (AdminNotfound e){
            log.info("Admin not found");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }






}
