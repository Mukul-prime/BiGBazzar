package com.example.BigBazzarServer.utlity.Transformers;

import com.example.BigBazzarServer.DTO.Request.NotificationcreateRequest;
import com.example.BigBazzarServer.DTO.Response.ResponseNotification;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.NotificationCenter;

import java.util.Base64;

public class NotificationTransformer {
    public static NotificationCenter NotificationRequesttoNotificationCenter(NotificationcreateRequest NotificationcreateRequest, Customer customer) {
        return NotificationCenter.builder()
                .productName(NotificationcreateRequest.getProductName())
                .productDescription(NotificationcreateRequest.getProductDescription())
                .customer(customer)
                .build();
    }

    public static ResponseNotification NotificationtoResponseNotification(NotificationCenter notificationCenter) {
        String base64Image = null;

        if(notificationCenter.getProductImage() != null){
            base64Image = Base64.getEncoder()
                    .encodeToString(notificationCenter.getProductImage());
        }

        return ResponseNotification.builder()
                .id(notificationCenter.getId())
                .productName(notificationCenter.getProductName()).
                productDescription(notificationCenter.getProductDescription()).
                productimageurl(base64Image).
                build();

    }

}
