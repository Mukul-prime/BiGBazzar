package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDAO extends JpaRepository<Reviews ,Integer> {
}
