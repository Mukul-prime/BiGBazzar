package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.SellerRequest;
import com.example.BigBazzarServer.DTO.Request.VerifiedOTPseller;
import com.example.BigBazzarServer.DTO.Response.SellerResponse;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.Service.OTPService;
import com.example.BigBazzarServer.Service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Seller")
@Slf4j
public class SellerCustomer {

    @Autowired
    public SellerService sellerService;

    @Autowired
    private OTPService otpService;

    @GetMapping()
    public String welcome(){
        return  "Welcome Seller";

    }

    @PostMapping("/signup")
    public ResponseEntity<String> createSeller(@RequestBody  SellerRequest sellerRequest){
        log.info("Seller Input Receiving : {}", sellerRequest);
      try {
          log.info("Seller created ");
          String sellerResponse =  sellerService.createSeller(sellerRequest);
          return new ResponseEntity<>(sellerResponse, HttpStatus.CREATED);
      }
      catch (SellerNotFound e){
          log.warn("seller Not founded");
          return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
      }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> GetSellerByEmail(@PathVariable String email){
        log.info("Seller Input Receiving : {}", email);
        try{
            SellerResponse response = sellerService.GetSellerByEmail(email);
            return new ResponseEntity<>(response , HttpStatus.OK);
        }
        catch (SellerNotFound e){
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/IsVerified")
    public ResponseEntity<?> GetVerified(@RequestBody VerifiedOTPseller verifiedOTPseller){
        log.info("Seller Input Receiving : {}", verifiedOTPseller);
        try{
            String response = otpService.IVerfiedPlease(verifiedOTPseller);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (SellerNotFound e){
            log.warn("seller Not founded");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
