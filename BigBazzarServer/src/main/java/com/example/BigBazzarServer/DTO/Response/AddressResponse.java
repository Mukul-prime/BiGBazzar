package com.example.BigBazzarServer.DTO.Response;


import com.example.BigBazzarServer.DTO.Request.StateRequest;
import com.example.BigBazzarServer.Model.City;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.State;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponse {
    private String houseNO;
    private String pinCode;
    private StateResponse state;
    private CityResponse city;
    private CustomerResponse customer;



}
