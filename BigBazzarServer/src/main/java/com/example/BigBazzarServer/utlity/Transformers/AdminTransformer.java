package com.example.BigBazzarServer.utlity.Transformers;


import com.example.BigBazzarServer.DTO.Request.AdminRequest;
import com.example.BigBazzarServer.Model.Admins;

public class AdminTransformer {
    public static Admins AdminRequesttoAdmin(AdminRequest adminRequest) {
        return Admins.builder()
                .username(adminRequest.getUsername())
                .build();


    }
}
