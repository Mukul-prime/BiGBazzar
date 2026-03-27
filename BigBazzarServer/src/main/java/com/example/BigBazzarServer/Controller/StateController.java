package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.StateRequest;
import com.example.BigBazzarServer.DTO.Request.UpdateStateRequest;
import com.example.BigBazzarServer.DTO.Response.StateResponse;
import com.example.BigBazzarServer.Exception.AdminNotfound;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/State")
@Slf4j
public class StateController {

    @Autowired
    public StateService service;

    @GetMapping()
    public String Welcome_message(){
        return "welcome state";
    }

//    Create a state

  @PostMapping("/")
    public ResponseEntity<?> createState(Authentication authentication,@RequestBody  StateRequest stateRequest){
        log.info("Input is "+stateRequest);
      try {
          log.info("Created ");
          String email = authentication.getName();

          StateResponse response =  service.createState(stateRequest, email);
          return new ResponseEntity<>(response, HttpStatus.CREATED);
      }
      catch (SellerNotFound e){
          log.warn("state not  created");
          return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
      }
    }

//    Update state Name
    @PutMapping("/State")
    public ResponseEntity<?> UpdateStateName(Authentication authentication,@RequestBody UpdateStateRequest updateStateRequest){
        log.info("Input is "+updateStateRequest.getNew_name());
        try{
            String email = authentication.getName();
            System.out.println(updateStateRequest.getStatecode());
            String response = service.UpdateStatename(updateStateRequest , email);
            return  new ResponseEntity<>(response ,  HttpStatus.OK);
        }
        catch (AdminNotfound  | SellerNotFound e){
            log.warn("state not  updated");
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }


//    Delete a Stata
    @DeleteMapping("/State")
    public  ResponseEntity<?> DeleteState(@RequestBody  UpdateStateRequest updateStateRequest ,Authentication authentication){
        log.info("Input is "+updateStateRequest);
        try{
            String email =  authentication.getName();
            String response = service.DeleteState(updateStateRequest , email);
            return  new ResponseEntity<>(response , HttpStatus.OK);
        }
        catch (SellerNotFound | AdminNotfound e){
            log.warn("state not  deleted");
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }


    }

}
