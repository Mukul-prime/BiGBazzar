package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.Address_forSeller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Addess_of_Seller extends JpaRepository<Address_forSeller ,Integer > {
    Address_forSeller findBySeller_sellerId(int id);}
