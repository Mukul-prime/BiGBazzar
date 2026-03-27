package com.example.BigBazzarServer.DTO.Request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemRequest {
    private int productId;
    private int quantity;
}
