package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.*;
import com.example.BigBazzarServer.DTO.Response.CustomerResponse;
import com.example.BigBazzarServer.DTO.Response.OrderItemResponse;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Exception.*;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.utlity.Transformers.CustomerTransformer;
import com.example.BigBazzarServer.utlity.Transformers.ProductTransformers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.example.BigBazzarServer.Custome_methods.OTP_GENERATOR.generateOTP;
//import static com.example.BigBazzarServer.Service.OTPService.sendEmail;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class CustomerService {




    PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();


    private final CustomerDAO customerDAO;

    private final OTPService otpService;

    private final SellerDAO sellerDAO;

    private final ProductDAO productDAO;


    public String createCustomer(CustomerRequest customerRequest) {

        // Email check
        if (customerDAO.findByEmail(customerRequest.getEmail()) != null) {
            throw new CustomerEmailAlreadyExists("Email already exists");
        }

        // Mobile number check
        if (customerDAO.findByMobileNo(customerRequest.getMobileNo()) != null) {
            throw new CustomerNumberExist("Mobile number already exists");
        }

        // Username check
        if (customerDAO.findByUsername(customerRequest.getUsername()) != null) {
            throw new CustomerUsernameAlreadyExisits("Username already exists");
        }

        Seller seller = sellerDAO.findByEmail(customerRequest.getEmail());
        if (seller != null && seller.getEmail().equals(customerRequest.getEmail())) {
            throw new EmailAlreadyExists("Email already used by seller");
        }


        Customer customer =
                CustomerTransformer.customerRequestToCustomer(customerRequest);

        customer.setRoles("ROLE_CUSTOMER");
        customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        String otps = generateOTP();
        customer.setVerified(false);
        customer.setOtp(otps);
        customerDAO.save(customer);
       otpService.sendEmail(customer,otps);



        // OTP generate


        return "Customer Created Successfully";
    }


    public String updateName(InformationRequests informationRequests){
        Customer customer = customerDAO.findByEmail(informationRequests.getEmail());
        if(customer == null){
            throw new CustomerNotCreated("Not exist");
        }


        customer.setName(informationRequests.getName());
        Customer r = customerDAO.save(customer);

        return  "updated Name";
    }

    public String UpdateEmail(ChangeEmail changeEmail){
        Customer customer = customerDAO.findByEmail(changeEmail.getEmail());
        if(customer == null){
            throw new CustomerNotFound("Not exist");
        }

        customer.setEmail(changeEmail.getNew_email());
        customerDAO.save(customer);
        return "Updated Email";
    }



        public String updateYear(UpdateYear updateYear){
        Customer customer = customerDAO.findByEmail(updateYear.getYear());
        if(customer == null){
            throw new CustomerNotFound("Not exist");

        }

        customer.setYear(Integer.parseInt(updateYear.getYear()));
        customerDAO.save(customer);
        return "Updated Year";
    }

    public CustomerResponse getCustomer(String email){
        Customer customer = customerDAO.findByEmail(email);
        if(customer == null){
            throw new CustomerNotFound("Not exist");

        }
        int currYear = 2025;
        int brith_year = customer.getYear();
        customer.setYear(currYear-brith_year);

        return CustomerTransformer.customerToCustomerResponse(customer) ;
    }
   public List<ProductResponse> getByname(String productName){
         List<ProductResponse> productResponse = ProductTransformers.productToProductResponsev2(productDAO.getByname(productName));
          if(productResponse.isEmpty()){
              throw new ProductNotFound("Not exist");
          }
          return productResponse;

    }

}

