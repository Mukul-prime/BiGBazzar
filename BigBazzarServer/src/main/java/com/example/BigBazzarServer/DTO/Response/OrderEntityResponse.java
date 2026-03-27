package com.example.BigBazzarServer.DTO.Response;

import com.example.BigBazzarServer.utlity.Enum.Status;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderEntityResponse {
    private int orderId;               // DB se generate
    private Date createdAt;            // order ka timestamp
    private Status status;             // PLACED, CONFIRMED, CANCELLED

    private int customerId;            // customer ID
    private String customerName;       // customer name

    private List<OrderItemResponse> orderItems;
}
