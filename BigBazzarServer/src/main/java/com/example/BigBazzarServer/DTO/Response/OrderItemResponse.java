package com.example.BigBazzarServer.DTO.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemResponse {
    private int orderItemId;    // DB se auto generate
    private int productId;      // product ID
    private String productName;
    private double price;// product ka naam
    private int quantity;
}
