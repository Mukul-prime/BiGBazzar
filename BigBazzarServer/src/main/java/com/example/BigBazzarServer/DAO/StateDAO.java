package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateDAO extends JpaRepository<State, Integer> {

    State findByName(String name);

    State findByStateCode(String StateCode);
}
