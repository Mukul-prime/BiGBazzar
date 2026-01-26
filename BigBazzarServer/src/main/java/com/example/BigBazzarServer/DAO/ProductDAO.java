package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product ,Integer> {

//    List<Product> findAll();

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    List<Product> getByname(@Param("name") String name);

}
