package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.CityRequest;
import com.example.BigBazzarServer.DTO.Request.DeleteCity;
import com.example.BigBazzarServer.DTO.Request.UpdateCity;
import com.example.BigBazzarServer.DTO.Response.CityResponse;
import com.example.BigBazzarServer.Exception.AdminNotfound;
import com.example.BigBazzarServer.Exception.CityNotFound;
import com.example.BigBazzarServer.Exception.StateNotFounded;
import com.example.BigBazzarServer.Service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
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
// create a city
    @PostMapping("/stateCode/{stateCode}")
    public ResponseEntity<?> createCity(Authentication authentication,@PathVariable String stateCode, @RequestBody  CityRequest cityRequest){
        log.info("Input receiving");
        try
        {
            String email = authentication.getName();
            CityResponse response = cityService.createCity( email,stateCode,cityRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }
        catch (StateNotFounded e){
            log.warn("City not exist");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

//    update city name
    @PutMapping("/City")
    public ResponseEntity<?> updateCityname(@RequestBody UpdateCity updateCity , Authentication authentication){
        log.info("Input receiving");
        try{
            String email  = authentication.getName();
            String response  = cityService.updateName(updateCity, email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (CityNotFound | AdminNotfound e){
            log.warn("City not found");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
    }


//    Detele a City
    @DeleteMapping("/City")
    public ResponseEntity<?> DeleteCityName(@RequestBody DeleteCity deleteCity, Authentication authentication){
        log.info("Input receiving");
        try{
            String email  = authentication.getName();
            String response = cityService.DeleteCity(deleteCity.getName() , email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (CityNotFound | AdminNotfound e){
            log.warn("City not found");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

        }

    }




}
