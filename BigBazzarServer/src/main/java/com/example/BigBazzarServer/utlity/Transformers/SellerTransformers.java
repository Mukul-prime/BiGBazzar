package com.example.BigBazzarServer.utlity.Transformers;

import com.example.BigBazzarServer.DTO.Request.SellerRequest;
import com.example.BigBazzarServer.DTO.Response.SellerResponse;
import com.example.BigBazzarServer.Model.Seller;

public class SellerTransformers {
    public static Seller sellerRequestTOSeller(SellerRequest sellerRequest){
        return Seller.builder()
                .username(sellerRequest.getUsername())
                .password(sellerRequest.getPassword())
                .name(sellerRequest.getName())
                .age(sellerRequest.getAge())
                .gst(sellerRequest.getGst())
                .email(sellerRequest.getEmail())
                .build();
    }

    public static SellerResponse sellerTosellerResponse(Seller seller){
        return SellerResponse.builder()
                .name(seller.getName())
                .gst(seller.getGst())
                .email(seller.getEmail())
                .build();
    }
}
