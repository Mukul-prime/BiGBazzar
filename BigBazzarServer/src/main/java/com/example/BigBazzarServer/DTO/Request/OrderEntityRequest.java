package com.example.BigBazzarServer.DTO.Request;

import com.example.BigBazzarServer.Model.OrderItem;
import com.example.BigBazzarServer.utlity.Enum.Status;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderEntityRequest {
    private int customerId;
    private Status status;
    private List<OrderItemRequest> orderItems;
}
