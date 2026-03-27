package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.*;
import com.example.BigBazzarServer.DTO.Response.CustomerResponse;
import com.example.BigBazzarServer.DTO.Response.NotificationCustomerResponse;
import com.example.BigBazzarServer.Exception.CustomerAlreadyExist;
import com.example.BigBazzarServer.Exception.CustomerNotCreated;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
//import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Exception.NotificationNotFound;
import com.example.BigBazzarServer.Service.CustomerService;
//import com.example.BigBazzarServer.annotations.Print_Hello;
import com.example.BigBazzarServer.Service.OTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
//    create a Customer
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

//    update user name
    @PutMapping("/UN")
    public ResponseEntity<?> updateName(@RequestBody InformationRequests informationRequests , Authentication authentication){
        try {
            String email = authentication.getName();
            String ans = customerService.updateName(informationRequests , email);
            return new ResponseEntity<>(ans , HttpStatus.OK);
        }
        catch (CustomerNotCreated e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }

    }
//    update email

    @PutMapping("/UE")
    public ResponseEntity<?> UpdateEmail(@RequestBody ChangeEmail changeEmail ,Authentication authentication){
        log.info("Input Receiving");
        try{
            String email =  authentication.getName();
            String response = customerService.UpdateEmail(changeEmail , email);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (CustomerNotFound e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
//update year

    @PutMapping("/UY")
    public ResponseEntity<?> updateYear(@RequestBody UpdateYear updateYear,
    Authentication authentication){
        log.info("Input Receivings"+authentication.getName());
        try {
            String email = authentication.getName();

            String response = customerService.updateYear(updateYear ,email );
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (CustomerNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

//    get customer details

    @GetMapping("/Customer")
    public ResponseEntity<?> getCustomerbyemail(Authentication authentication){
        log.info("Input Receivings"+authentication.getName());
        try {
            String email = authentication.getName();
            CustomerResponse customerResponse = customerService.getCustomer(email);
            return new ResponseEntity<>(customerResponse, HttpStatus.OK);
        }catch (CustomerNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

//    get verified

    @GetMapping("/Verified")
   public ResponseEntity<?> Getverified(@RequestBody VerifiedOTP verifiedOTP , Authentication authentication){
        log.info("Input Receivings"+authentication.getName());
        try{
            String email = authentication.getName();

            String response = otpService.IsVerfiedPlease(verifiedOTP , email);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (CustomerNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
   }


//   update password
   @PutMapping("/Customers")
  public  ResponseEntity<?> updatepassword(Authentication authentication ,@RequestBody UpdatePasswordRequest updatePasswordRequest){
        log.info("update password " + authentication.getName());
        try{
            String email = authentication.getName();
            String response = customerService.UpdatePassword(email,updatePasswordRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (CustomerNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
   }
//   @GetMapping("/name/{name}")
//   public ResponseEntity<?> GetProductByname(@PathVariable String name){
//        log.info("Input Receivings"+name);
//
//        try{
//
//            List<ProductResponse> response = customerService.getByname(name);
//            return new ResponseEntity<>(response , HttpStatus.OK);
//
//        }
//        catch (ProductNotFound e){
//            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
//        }
//
//   }

//Get all Notification ans
    @GetMapping("/GetallAns")
    public ResponseEntity<?> notificationCustomerResponse(Authentication authentication){
        log.info("Input Receivings"+authentication.getName());
        try{
            String email = authentication.getName();
            List<NotificationCustomerResponse> ans = customerService.notificationCustomerResponse(email);
            return new ResponseEntity<>(ans , HttpStatus.ACCEPTED);
        }
        catch (CustomerNotFound | NotificationNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }

    }


}
