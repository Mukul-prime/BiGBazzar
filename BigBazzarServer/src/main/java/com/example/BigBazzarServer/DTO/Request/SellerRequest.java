package com.example.BigBazzarServer.DTO.Request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SellerRequest {

    private String username;
    private String password;
    private String name;
    private int age;


    private String gst;


    private String email;



}
