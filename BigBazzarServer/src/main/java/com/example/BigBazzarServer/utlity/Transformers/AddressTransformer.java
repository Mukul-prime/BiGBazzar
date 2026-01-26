package com.example.BigBazzarServer.utlity.Transformers;

import com.example.BigBazzarServer.DTO.Request.AddressRequest;
import com.example.BigBazzarServer.DTO.Response.AddressResponse;
import com.example.BigBazzarServer.Model.Address;
import com.example.BigBazzarServer.Model.City;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.State;

public class AddressTransformer {


    public static Address addressRequesttoAddress(AddressRequest addressRequest
                                                  ){
        return Address.builder()

                .houseNo(addressRequest.getHouseNo())
                .pinCode(addressRequest.getPinCode())
                .build();


    }

    public static AddressResponse addressToAddressResponse(Address address){
        return AddressResponse.builder()
                .houseNO(address.getHouseNo())
                .pinCode(address.getPinCode())
                .customer(CustomerTransformer.customerToCustomerResponse(address.getCustomer()))
//                .state(StateTransformers.stateResponseToState(address.getState()))
                .city(CityTransformer.citytoCityResponse(address.getCity()))
                .build();
    }


}
