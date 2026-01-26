package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.*;
import com.example.BigBazzarServer.DTO.Response.CustomerResponse;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Exception.CustomerAlreadyExist;
import com.example.BigBazzarServer.Exception.CustomerNotCreated;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
//import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Exception.ProductNotFound;
import com.example.BigBazzarServer.Service.CustomerService;
//import com.example.BigBazzarServer.annotations.Print_Hello;
import com.example.BigBazzarServer.Service.OTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Customer")
@Slf4j
public class CustomerController {

    @Autowired
    public CustomerService customerService;

    @Autowired
    private OTPService otpService;

    @GetMapping
    public String Welcome(){
        return "Welcome Customer";
    }
    @PostMapping("/signup")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRequest customerRequest){
        try{
          String customerResponse =  customerService.createCustomer(customerRequest);
          return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        }
        catch (CustomerAlreadyExist e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/UN")
    public ResponseEntity<?> updateName(@RequestBody InformationRequests informationRequests){
        try {
            String ans = customerService.updateName(informationRequests);
            return new ResponseEntity<>(ans , HttpStatus.OK);
        }
        catch (CustomerNotCreated e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/UE")
    public ResponseEntity<?> UpdateEmail(@RequestBody ChangeEmail changeEmail){
        log.info("Input Receiving");
        try{
            String response = customerService.UpdateEmail(changeEmail);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (CustomerNotFound e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/UY")
    public ResponseEntity<?> updateYear(@RequestBody UpdateYear updateYear){
        log.info("Input Receivings"+updateYear.getEmail()+" "+updateYear.getYear());
        try {
            String response = customerService.updateYear(updateYear);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (CustomerNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getCustomerbyemail(@PathVariable  String email){
        log.info("Input Receivings"+email);
        try {
            CustomerResponse customerResponse = customerService.getCustomer(email);
            return new ResponseEntity<>(customerResponse, HttpStatus.OK);
        }catch (CustomerNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/Verified")
   public ResponseEntity<?> Getverified(@RequestBody VerifiedOTP verifiedOTP){
        log.info("Input Receivings"+verifiedOTP.getUsername());
        try{
            String response = otpService.IsVerfiedPlease(verifiedOTP);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (CustomerNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
   }

   @GetMapping("/name/{name}")
   public ResponseEntity<?> GetProductByname(@PathVariable String name){
        log.info("Input Receivings"+name);

        try{

            List<ProductResponse> response = customerService.getByname(name);
            return new ResponseEntity<>(response , HttpStatus.OK);

        }
        catch (ProductNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }

   }


}
