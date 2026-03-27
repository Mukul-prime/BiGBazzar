package com.example.BigBazzarServer.DTO.Request;

import com.example.BigBazzarServer.utlity.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderChangeStatus {
    private Status status;
    private int orderid;
}
