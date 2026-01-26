package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.StateRequest;
import com.example.BigBazzarServer.DTO.Response.StateResponse;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


  @PostMapping
    public ResponseEntity<?> createState(@RequestBody  StateRequest stateRequest){
        log.info("Input is "+stateRequest);
      try {
          log.info("Created ");
          StateResponse response =  service.createState(stateRequest);
          return new ResponseEntity<>(response, HttpStatus.CREATED);
      }
      catch (SellerNotFound e){
          log.warn("state not  created");
          return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
      }
    }

}
