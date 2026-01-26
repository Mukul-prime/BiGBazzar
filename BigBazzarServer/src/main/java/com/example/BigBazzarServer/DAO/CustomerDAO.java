package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDAO extends JpaRepository<Customer,Integer> {

    Customer findByEmail(String email);
    Customer findByMobileNo(String mobileNo);
    Customer findByUsername(String username);


}
