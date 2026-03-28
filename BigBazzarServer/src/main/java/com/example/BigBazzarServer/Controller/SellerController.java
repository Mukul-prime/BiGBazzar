package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.SellerNotified;
import com.example.BigBazzarServer.DTO.Request.SellerRequest;
import com.example.BigBazzarServer.DTO.Request.UpdatePasswordRequest;
import com.example.BigBazzarServer.DTO.Request.VerifiedOTPseller;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.DTO.Response.ResponseNotification;
import com.example.BigBazzarServer.DTO.Response.SellerResponse;
import com.example.BigBazzarServer.Exception.DoesNotHaveProduct;
import com.example.BigBazzarServer.Exception.NotificationNotFound;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Model.NotificationCenter;
import com.example.BigBazzarServer.Service.OTPService;
import com.example.BigBazzarServer.Service.SellerService;
import com.example.BigBazzarServer.utlity.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Seller")
@Slf4j
public class SellerController {

    @Autowired
    public SellerService sellerService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public String welcome() {
        return "Welcome Seller";

    }
// Create a Seller
    @PostMapping("/signup")
    public ResponseEntity<String> createSeller(@RequestBody SellerRequest sellerRequest) {
        log.info("Seller Input Receiving : {}", sellerRequest);
        try {
            log.info("Seller created ");
            String sellerResponse = sellerService.createSeller(sellerRequest);
            return new ResponseEntity<>(sellerResponse, HttpStatus.CREATED);
        } catch (SellerNotFound e) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    Get Seller

    @GetMapping("/")
    public ResponseEntity<?> GetSellerByEmail(Authentication authentication) {
        log.info("Seller Input Receiving : {}", authentication.getName());
        try {
            String email = authentication.getName();
            SellerResponse response = sellerService.GetSellerByEmail(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SellerNotFound e) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


//    Get Verified
    @GetMapping("/IsVerified")
    public ResponseEntity<?> GetVerified(@RequestBody VerifiedOTPseller verifiedOTPseller) {
        log.info("Seller Input Receiving : {}", verifiedOTPseller);
        try {
            String response = otpService.IVerfiedPlease(verifiedOTPseller);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SellerNotFound e) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


//    Get product insert by Selller
    @GetMapping("/Seller")
    public ResponseEntity<?> GetproductBySellerEmail(Authentication authentication) {
        log.info(authentication.getName());
        try {
            String email = authentication.getName();
            List<ProductResponse> responses = sellerService.getAllProducts(email);
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (SellerNotFound | DoesNotHaveProduct w) {
            return new ResponseEntity<>(w.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    Get Seller Category it will create by one seller

    @GetMapping("/Category")
    public ResponseEntity<?> getSellerCategory(Authentication authentication) {

        String email = authentication.getName();

        List<String> response =
                sellerService.getAllCategories(email);

        return ResponseEntity.ok(response);
    }

//seller password update
    @PutMapping("/Sellerup")
    public ResponseEntity<?> updatepassword(Authentication authentication , @RequestBody UpdatePasswordRequest updatePasswordRequest){
        log.info(updatePasswordRequest.getPassword());
        try{
            String email = authentication.getName();
            String reponse = sellerService.updatepassword(email,  updatePasswordRequest);
            return new ResponseEntity<>(reponse, HttpStatus.OK);


        }
        catch (SellerNotFound e) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    update seller email
    @PutMapping("/Sellerue")
    public ResponseEntity<?> updatemail(Authentication authentication ,@RequestParam String email ){

        log.info(authentication.getName());
        try{
            String emai = authentication.getName();
            String response = sellerService.updateEmail(emai, email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (SellerNotFound e) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    Seller update name

    @PutMapping("/Sellerun")
    public ResponseEntity<?> updateUsername(Authentication authentication , @RequestParam String new_name){
        log.info(authentication.getName());
        try{
            String emai = authentication.getName();
            String response = sellerService.updateUsername(emai , new_name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (SellerNotFound e) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

//    Count notification

    @GetMapping("/Sellercount")
    public ResponseEntity<?> CountData(Authentication authentication) {
        log.info(authentication.getName());
        try{
            String email =  authentication.getName();
            List<Long> response = Collections.singletonList(sellerService.CountNotification(email));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (SellerNotFound e) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

//    get all notification

    @GetMapping("/SellerNoti")
    public ResponseEntity<?> getallData(Authentication authentication) {
        log.info(authentication.getName());
        try{
            String email =  authentication.getName();
            List<ResponseNotification> response = sellerService.getallNotificatios(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (SellerNotFound e) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


//    Update Notification ans
    @PutMapping("/creates")
    public ResponseEntity<?> answerit(Authentication authentication , @RequestBody SellerNotified notified){
        log.info(authentication.getName());
        try{
            String email = authentication.getName();
            String ans  = sellerService.AnswerNotification(email, notified);
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }catch (SellerNotFound | NotificationNotFound e) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/deactive")
    public ResponseEntity<?> deactiveid(Authentication authentication){
        log.info(authentication.getName());
        try{
            String email = authentication.getName();
            String response =  sellerService.deactivateUser(email);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch (SellerNotFound w) {
            log.warn("seller Not founded");
            return new ResponseEntity<>(w.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
