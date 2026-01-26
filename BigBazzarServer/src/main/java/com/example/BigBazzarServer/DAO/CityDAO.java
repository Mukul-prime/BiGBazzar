package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.City;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDAO extends JpaRepository<City ,Integer> {
}
