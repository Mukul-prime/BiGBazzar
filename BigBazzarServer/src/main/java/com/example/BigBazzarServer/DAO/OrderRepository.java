package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
}
