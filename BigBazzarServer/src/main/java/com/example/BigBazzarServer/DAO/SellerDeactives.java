package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.SellerDeactive;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SellerDeactives extends JpaRepository<SellerDeactive, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE SellerDeactive a SET a.active = false WHERE a.seller.sellerId = :id")
    void deactivateCustomer(@Param("id") int id);

    SellerDeactive findBySeller_sellerId(int id);

    @Query(value = "SELECT * FROM seller_deactive WHERE active = 1", nativeQuery = true)
    List<SellerDeactive> getalldeactivate();
}
