package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.CityRequest;
import com.example.BigBazzarServer.DTO.Response.CityResponse;
import com.example.BigBazzarServer.Exception.CityNotFound;
import com.example.BigBazzarServer.Exception.StateNotFounded;
import com.example.BigBazzarServer.Service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/City")
@Slf4j
public class CityController {

    @Autowired
    public CityService cityService;

    @GetMapping
    public String Welcome (){
        return "Welcome City";

    }

    @PostMapping("/stateCode/{stateCode}")
    public ResponseEntity<?> createCity(@PathVariable String stateCode, @RequestBody  CityRequest cityRequest){
        log.info("Input receiving");
        try
        {
            CityResponse response = cityService.createCity(stateCode,cityRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }
        catch (StateNotFounded e){
            log.warn("City not exist");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
