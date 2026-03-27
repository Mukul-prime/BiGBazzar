package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.NotificationCenter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.Notification;
import java.util.List;

@Repository
@Transactional
public interface NotificationDAO  extends JpaRepository<NotificationCenter , Long> {
  List<NotificationCenter> findByCustomer_CustomerId(long customerId);
  boolean existsByCustomer_CustomerIdAndProductName(Long customerId, String productName);

}
