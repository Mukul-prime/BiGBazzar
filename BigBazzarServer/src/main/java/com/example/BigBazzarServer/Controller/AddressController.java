package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.AddressRequest;
import com.example.BigBazzarServer.DTO.Response.AddressResponse;
import com.example.BigBazzarServer.Exception.CityNotFound;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Exception.StateNotFounded;
import com.example.BigBazzarServer.Model.Address;
import com.example.BigBazzarServer.Service.AddressService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;

@RestController
@RequestMapping("/api/v1/Customer/Address")
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public String welcome_address(){
        return"Welcome to Address";
    }

    @PostMapping
    public ResponseEntity<?> CreateAddress(@RequestBody AddressRequest addressRequest){
        log.info("Received request to Create Address : {}", addressRequest);
        try{
            AddressResponse response = addressService.createAddress(addressRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (CityNotFound | StateNotFounded | CustomerNotFound e ){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }


}
