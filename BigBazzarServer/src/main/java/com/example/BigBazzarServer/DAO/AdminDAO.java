package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.Admins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDAO extends JpaRepository<Admins, Long> {
    Admins findByEmail(String email);
    Admins findByUsername(String username);

}
