package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.SellerRequest;
import com.example.BigBazzarServer.DTO.Response.SellerResponse;
import com.example.BigBazzarServer.Exception.EmailAlreadyExists;
import com.example.BigBazzarServer.Exception.GstAlreadyExisit;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Exception.Useralreadycreated;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.utlity.Transformers.SellerTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.BigBazzarServer.Custome_methods.OTP_GENERATOR.generateOTP;

@Service
public class SellerService {

    @Autowired
    public SellerDAO sellerDAO;

    @Autowired public OTPService otpService;

    @Autowired public CustomerDAO customerDAO;


    PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();

    public String createSeller(SellerRequest sellerRequest){

        // 1. Seller email already exists
        if (sellerDAO.findByEmail(sellerRequest.getEmail()) != null) {
            throw new EmailAlreadyExists("Seller email already exists");
        }

        if(sellerDAO.findByGst(sellerRequest.getGst()) != null) {
            throw new GstAlreadyExisit("Gst Number already exists");
        }

        // 2. Check customer existence
        Customer customer = customerDAO.findByUsername(sellerRequest.getUsername());

        if(customer != null){

            // Email conflict
            if(customer.getEmail().equals(sellerRequest.getEmail())){
                throw new EmailAlreadyExists("Email already used by customer");
            }

            // Username already registered
            if(customer.getUsername().equals(sellerRequest.getUsername())){
                throw new Useralreadycreated("User already exists");
            }
        }

        // 3. Create seller
        Seller seller = SellerTransformers.sellerRequestTOSeller(sellerRequest);
        seller.setRoles("ROLE_SELLER");
        seller.setPassword(passwordEncoder.encode(sellerRequest.getPassword()));
        seller.setVerifiedis(false);

        String otp = generateOTP();
        seller.setOtp(otp);

        sellerDAO.save(seller);
        otpService.sellersendEmail(seller, otp);

        return "Seller created successfully. OTP sent to email.";
    }


    public SellerResponse GetSellerByEmail(String email){
        Seller seller = sellerDAO.findByEmail(email);
        if(seller == null){
            throw new SellerNotFound("Sorry Not Exist");
        }
        return SellerTransformers.sellerTosellerResponse(seller);
    }



}
