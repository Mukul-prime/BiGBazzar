package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AddressDAO extends JpaRepository<Address,Integer> {
}
