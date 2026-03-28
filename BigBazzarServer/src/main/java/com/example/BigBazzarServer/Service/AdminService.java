package com.example.BigBazzarServer.Service;


import com.example.BigBazzarServer.DAO.*;
import com.example.BigBazzarServer.DTO.Request.AdminRequest;
import com.example.BigBazzarServer.DTO.Request.UpdatePasswordRequest;
import com.example.BigBazzarServer.Exception.*;
import com.example.BigBazzarServer.Model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AdminDAO adminDAO;
    private final CustomerDAO customerDAO;
    private final SellerDAO sellerDAO;
    private final DeleteCustomerDAO deleteCustomerDAO;
    private final SellerDeactives sellerDeactives;
    private final Addess_of_Seller addessOfSeller;



    public String createAdmin(AdminRequest adminRequest) {
        if (adminDAO.findByEmail(adminRequest.getEmail()) != null) {
            throw new EmailAlreadyExists("Admin email already exists");
        }

        if (adminDAO.findByUsername(adminRequest.getUsername()) != null) {
            throw new Useralreadycreated("Admin username already exists");
        }

        Customer customer = customerDAO.findByEmail(adminRequest.getEmail());
        if (customer != null) {
            throw  new CustomerEmailAlreadyExists("Customer email already exists");
        }

        Seller seller = sellerDAO.findByEmail(adminRequest.getEmail());
        if (seller != null) {
            throw new SellerEmailAlreadyExist("Seller email already exists");
        }

        Admins admin = Admins.builder()
                .email(adminRequest.getEmail())
                .role("ROLE_ADMIN")
                .password(passwordEncoder.encode(adminRequest.getPassword()))
                .username(adminRequest.getUsername())
                .build();

        adminDAO.save(admin);
        return "Admin created successfully";

    }


    public String UpdatePassword(UpdatePasswordRequest updatePasswordRequest, String email){
        Admins admin = adminDAO.findByEmail(email);
        if(admin == null){
            throw new AdminNotfound("Invalid Account");
        }

        admin.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
        adminDAO.save(admin);
        return "Admin updated successfully";

    }


     public String deletecustomer(String email){
        Admins admins = adminDAO.findByEmail(email);
        if(admins == null){
            throw new AdminNotfound("Invalid Account");
        }
        List<Customer> customers = new ArrayList<>();
        List<Deactivate> deactivateList = deleteCustomerDAO.getalldeactivate();
        for(Deactivate customer : deactivateList){
            customers.add(customer.getCustomer());

        }
        deleteCustomerDAO.deleteAll(deactivateList);
        customerDAO.deleteAll(customers);
        return "Deleted successfully";
     }


     public String deleteseller(String email){
        Admins admins = adminDAO.findByEmail(email);
        if(admins == null){
            throw new AdminNotfound("Invalid Account");
        }
        List<Seller> sellers = new ArrayList<>();
        List<SellerDeactive> deactivates = sellerDeactives.getalldeactivate();
        for(SellerDeactive deactivate : deactivates){
            sellers.add(deactivate.getSeller());
        }


        sellerDeactives.deleteAll(deactivates);

        sellerDAO.deleteAll(sellers);
        return "Deleted successfully";
     }
}
