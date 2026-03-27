package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.CustomerDAO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
     private CustomerService customerService;

    void whenCustomerIdExist_thenReturnCustomer(){
         int id = 1;
         String email = "Mukulvats@gmail.com";


    }
}
