package com.example.BigBazzarServer.DAO;

//import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.Deactivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeleteCustomerDAO extends JpaRepository<Deactivate, Long> {

    @Modifying
    @Query("UPDATE Deactivate d SET d.active = false WHERE d.customer.customerId = :id")
    void deactivateCustomer(@Param("id") int id);

//    @Modifying
//    @Query("UPDATE Deactivate d set d.active = true where d.customer.customerID =:id")
//    void ActivateAccount(@Param("id") int id );

    Deactivate findByCustomer_customerId(int customerId);

    @Query(value = "SELECT * FROM deactivate WHERE active = 1", nativeQuery = true)
    List<Deactivate> getalldeactivate();

}
