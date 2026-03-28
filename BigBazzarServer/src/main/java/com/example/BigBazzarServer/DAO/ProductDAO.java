package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.utlity.Enum.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ProductDAO extends JpaRepository<Product ,Integer> {



    @Query("SELECT p FROM Product p WHERE p.name = :name")
    List<Product> getByname(@Param("name") String name);
    List<Product> findBySellerSellerId(int sellerId);


    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.seller.sellerId = :sellerId")
    Set<Category> findDistinctCategoryBySellerId(@Param("sellerId") int sellerId);

    @Query("SELECT p FROM Product p WHERE p.category = 'GROCERY' AND p.expiryDate BETWEEN :today AND :futureDate")
    List<Product> findExpiringProducts(
            @Param("today") LocalDate today,
            @Param("futureDate") LocalDate futureDate
    );

    List<Product> findBySeller_sellerId(int sellerId);
}
