package com.example.BigBazzarServer.Repositary;

import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.utlity.Enum.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@AutoConfigureDataJpa

public class CustomerRepoTest {

    @Autowired
    CustomerDAO customerDAO;

    @Test
    void should_verify_when_valid_Customer() {

        // arrange
        Customer customer_test = Customer.builder()
                .username("test")
                .password("123")
                .name("TestName")
                .roles("tester")
                .year(2004)
                .email("test@gmail.com")
                .gender(Gender.MALE)
                .mobileNo("1234567890")
                .build();

        // act
        Customer saveCustomer = customerDAO.save(customer_test);

        // assertion
        Assertions.assertNotNull(saveCustomer);
        Assertions.assertEquals("test", saveCustomer.getUsername());

    }
}
