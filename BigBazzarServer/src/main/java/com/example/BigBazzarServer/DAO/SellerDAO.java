package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SellerDAO extends JpaRepository<Seller,Integer> {

    Seller findByEmail(String email);
    Seller findByUsername(String username);
    Seller findByGst(String gst);


}
