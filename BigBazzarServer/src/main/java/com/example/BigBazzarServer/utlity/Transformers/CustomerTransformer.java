package com.example.BigBazzarServer.utlity.Transformers;

import com.example.BigBazzarServer.DTO.Request.CustomerRequest;
import com.example.BigBazzarServer.DTO.Response.CustomerResponse;
import com.example.BigBazzarServer.Model.Customer;

public class CustomerTransformer {

    public static Customer customerRequestToCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .username(customerRequest.getUsername())
                .password(customerRequest.getPassword())

                .name(customerRequest.getName())
                .year(customerRequest.getYear())
                .email(customerRequest.getEmail().toLowerCase())
                .gender(customerRequest.getGender())
                .mobileNo(customerRequest.getMobileNo())
                .build();
    }

    public static CustomerResponse customerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .age(customer.getYear())
                .build();
    }
}

