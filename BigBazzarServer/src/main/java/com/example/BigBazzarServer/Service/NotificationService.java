package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.NotificationBoxs;
import com.example.BigBazzarServer.DAO.NotificationDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.NotificationcreateRequest;
import com.example.BigBazzarServer.Exception.AlreadyNotifiedByCustomer;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.NotificationBox;
import com.example.BigBazzarServer.Model.NotificationCenter;
import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.utlity.Transformers.NotificationTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationDAO notificationDAO;
    private final CustomerDAO customerDAO;
    private final NotificationBoxs notificationBoxs;
    private final SellerDAO sellerDAO;

    public String notificationCretaed(NotificationcreateRequest notificationcreateRequest, String email) throws IOException {
        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFound("Customer not Created");
        }
        boolean istrue = notificationDAO.existsByCustomer_CustomerIdAndProductName((long)customer.getCustomerId() , notificationcreateRequest.getProductName());
        if(istrue){
            throw new AlreadyNotifiedByCustomer("you are already notified it ");
        }

        NotificationCenter notificationCenter =
                NotificationTransformer.
                        NotificationRequesttoNotificationCenter(notificationcreateRequest, customer);


        if (notificationcreateRequest.getProductImage() != null && !notificationcreateRequest.getProductImage().isEmpty()) {
            notificationCenter.setProductImage(notificationcreateRequest.getProductImage().getBytes());
        }
//        customer.setNotificationList((List<NotificationCenter>) notificationCenter);
        notificationDAO.save(notificationCenter);
        List<Seller> sellerList = sellerDAO.findAll();
        for(Seller seller1 : sellerList){

            NotificationBox notificationBox = new NotificationBox();
            notificationBox.setSeller(seller1);
            notificationBox.setNotificationCenter(notificationCenter);
            notificationBox.setNotified(false);
            notificationBoxs.save(notificationBox);
        }

        return "Notification Created";
    }

}
