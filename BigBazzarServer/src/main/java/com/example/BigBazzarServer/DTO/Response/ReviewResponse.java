package com.example.BigBazzarServer.DTO.Response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewResponse {

    private String reviewText;
    private CustomerResponse customer;
    private ProductResponse product;
    private Date date;
}
