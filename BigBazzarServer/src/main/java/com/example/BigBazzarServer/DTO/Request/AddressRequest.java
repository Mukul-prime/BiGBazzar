package com.example.BigBazzarServer.DTO.Request;


import com.example.BigBazzarServer.Model.City;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.State;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AddressRequest {

    private String houseNo;
    private String pinCode;
    private Integer customer;
    private Integer city;
    private String state;
}
