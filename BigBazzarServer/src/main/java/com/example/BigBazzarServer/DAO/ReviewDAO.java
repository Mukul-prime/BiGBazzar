package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewDAO extends JpaRepository<Reviews ,Integer> {


    List<Reviews> findByProduct_productId(int productId);


}
