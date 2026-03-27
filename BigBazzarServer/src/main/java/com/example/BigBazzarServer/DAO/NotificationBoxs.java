package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.NotificationBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationBoxs extends JpaRepository<NotificationBox, Long> {
    List<NotificationBox> findBySeller_SellerId(long sellerId);
    NotificationBox findByNotificationCenter_Id(long notificationCenterId);
}
