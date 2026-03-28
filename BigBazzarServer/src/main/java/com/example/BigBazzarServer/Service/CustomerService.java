package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.*;
import com.example.BigBazzarServer.DTO.Request.*;
import com.example.BigBazzarServer.DTO.Response.CustomerResponse;
import com.example.BigBazzarServer.DTO.Response.NotificationCustomerResponse;
import com.example.BigBazzarServer.DTO.Response.OrderItemResponse;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Exception.*;
import com.example.BigBazzarServer.Model.*;
import com.example.BigBazzarServer.utlity.Transformers.CustomerTransformer;
import com.example.BigBazzarServer.utlity.Transformers.ProductTransformers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.BigBazzarServer.Custome_methods.OTP_GENERATOR.generateOTP;
//import static com.example.BigBazzarServer.Service.OTPService.sendEmail;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CustomerService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    private final DeleteCustomerDAO deleteCustomerDAO;

    private final CustomerDAO customerDAO;

    private final OTPService otpService;

    private final SellerDAO sellerDAO;

    private final ProductDAO productDAO;

    private final NotificationDAO notificationDAO;

    private final NotificationBoxs notificationBoxs;


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


        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);

        customer.setRoles("ROLE_CUSTOMER");
        customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        String otps = generateOTP();
        customer.setVerified(false);
        customer.setOtp(otps);
        customerDAO.save(customer);
        otpService.sendEmail(customer, otps);


        return "Customer Created Successfully";
    }


    public String updateName(InformationRequests informationRequests, String email) {
        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotCreated("Not exist");
        }


        customer.setName(informationRequests.getName());
        customerDAO.save(customer);

        return "updated Name";
    }

    public String UpdateEmail(ChangeEmail changeEmail, String email) {
        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFound("Not exist");
        }

        customer.setEmail(changeEmail.getNew_email().toLowerCase());
        customerDAO.save(customer);
        return "Updated Email";
    }


    public String updateYear(UpdateYear updateYear, String email) {
        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFound("Not exist");

        }

        customer.setYear(Integer.parseInt(updateYear.getYear()));
        customerDAO.save(customer);
        return "Updated Year";
    }

    public CustomerResponse getCustomer(String email) {
        LocalDate localDate = LocalDate.now();
        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFound("Not exist");

        }
        int currYear = localDate.getYear();
        int brith_year = customer.getYear();
        customer.setYear(currYear - brith_year);

        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public List<ProductResponse> getByname(String productName) {
        List<ProductResponse> productResponse = ProductTransformers.productToProductResponsev2(productDAO.getByname(productName));
        if (productResponse.isEmpty()) {
            throw new ProductNotFound("Not exist");
        }
        return productResponse;

    }

    public String UpdatePassword(String email, UpdatePasswordRequest updatePasswordRequest) {
        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) throw new CustomerNotFound("Not exist");
        customer.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
        customerDAO.save(customer);
        return "Updated Password";
    }

    public List<NotificationCustomerResponse> notificationCustomerResponse(String email) {

        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFound("Not exist");
        }

        List<NotificationCenter> centers =
                notificationDAO.findByCustomer_CustomerId(customer.getCustomerId());

        if (centers.isEmpty()) {
            throw new NotificationNotFound("No create any Notification");
        }

        List<NotificationCustomerResponse> ans = new ArrayList<>();

        for (NotificationCenter notificationCenter : centers) {


            List<NotificationBox> boxes =
                    Collections.singletonList(notificationBoxs.findByNotificationCenter_Id(notificationCenter.getId()));

            for (NotificationBox box : boxes) {

                if (box.isNotified()) {


                    NotificationCustomerResponse response = new NotificationCustomerResponse();

                    Seller seller = box.getSeller();

                    response.setNameofSeller(seller.getName());
                    response.setEmail(seller.getEmail());
                    response.setProductDescription(notificationCenter.getProductDescription());
                    response.setProductName(notificationCenter.getProductName());


                    ans.add(response);
                }
            }
        }

        return ans;
    }



    public String deactivateUser(String email) {

        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFound("Customer not exist");
        }

        Deactivate deactivate = deleteCustomerDAO
                .findByCustomer_customerId(customer.getCustomerId());

        if (deactivate == null) {
            deactivate = Deactivate.builder()
                    .customer(customer)
                    .build();
        }

        deactivate.setActive(true);

        deleteCustomerDAO.save(deactivate);

        return "User Deactivated Successfully";
    }





}

