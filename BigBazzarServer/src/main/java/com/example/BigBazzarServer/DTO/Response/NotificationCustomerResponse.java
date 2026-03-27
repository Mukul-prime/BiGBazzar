package com.example.BigBazzarServer.DTO.Response;

import com.example.BigBazzarServer.Model.NotificationCenter;
import com.example.BigBazzarServer.Model.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class NotificationCustomerResponse {
    private String nameofSeller;
//    private String gstNo;
    private String Email;
    private String productDescription;
    private String productName;
//    private String urlimaeg;
}
