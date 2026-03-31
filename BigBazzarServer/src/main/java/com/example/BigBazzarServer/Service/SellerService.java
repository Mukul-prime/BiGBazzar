package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.*;
import com.example.BigBazzarServer.DTO.Request.SellerNotified;
import com.example.BigBazzarServer.DTO.Request.SellerRequest;
import com.example.BigBazzarServer.DTO.Request.UpdatePasswordRequest;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.DTO.Response.ResponseNotification;
import com.example.BigBazzarServer.DTO.Response.SellerResponse;
import com.example.BigBazzarServer.Exception.*;
import com.example.BigBazzarServer.Model.*;
import com.example.BigBazzarServer.utlity.Enum.Category;
import com.example.BigBazzarServer.utlity.Transformers.NotificationTransformer;
import com.example.BigBazzarServer.utlity.Transformers.ProductTransformers;
import com.example.BigBazzarServer.utlity.Transformers.SellerTransformers;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.BigBazzarServer.Custome_methods.OTP_GENERATOR.generateOTP;

@Service
public class SellerService {

    @Autowired
    public SellerDAO sellerDAO;

    @Autowired public NotificationBoxs notificationBoxs;

    @Autowired public OTPService otpService;

    @Autowired public CustomerDAO customerDAO;

    @Autowired public ProductDAO productDAO;

    @Autowired public NotificationDAO notificationDAO;

    @Autowired public SellerDeactives sellerDeactives;

    @Autowired public ProductTransformers productTransformers;


    PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();

    public String createSeller(SellerRequest sellerRequest){

        // 1. Seller email already exists
        if (sellerDAO.findByEmail(sellerRequest.getEmail()) != null) {
            throw new EmailAlreadyExists("Seller email already exists");
        }

        if(sellerDAO.findByGst(sellerRequest.getGst()) != null) {
            throw new GstAlreadyExisit("Gst Number already exists");
        }

        Customer customerByEmail = customerDAO.findByEmail(sellerRequest.getEmail());
        if (customerByEmail != null) {
            throw new EmailAlreadyExists("Email already used by customer");
        }

        Customer customerByUsername = customerDAO.findByUsername(sellerRequest.getUsername());
        if (customerByUsername != null) {
            throw new Useralreadycreated("User already exists");
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
    @Transactional
    public List<ProductResponse> getAllProducts(String email){

        Seller seller = sellerDAO.findByEmail(email);

        if(seller == null){
            throw new SellerNotFound("Seller not exist");
        }

        List<Product> products = seller.getProducts();

        if(products == null || products.isEmpty()){
            throw new DoesNotHaveProduct("No products found");
        }

        return products.stream()
                .map(productTransformers::productToProductResponse)
                .toList();
    }


    public List<String> getAllCategories(String email){
        Seller seller = sellerDAO.findByEmail(email);
        if(seller == null){
            throw new SellerNotFound("Seller not exist");
        }
        Set<Category> categorySet =
                productDAO.findDistinctCategoryBySellerId(seller.getSellerId());

        List<String> list = categorySet.stream()
                .map(Category::name)   // enum → String
                .toList();
        return list;


    }


    public String updateEmail(String email , String new_email ){
        Seller seller = sellerDAO.findByEmail(email);
        if(seller == null){
            throw new SellerNotFound("Seller not exist");

        }

        seller.setEmail(new_email);
        sellerDAO.save(seller);
        return "Seller updated successfully";


    }
    public String updatepassword(String email , UpdatePasswordRequest updatePasswordRequest){
        Seller seller = sellerDAO.findByEmail(email);
        if(seller == null){
            throw new SellerNotFound("Seller not exist");
        }
        seller.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
        sellerDAO.save(seller);
        return "Seller updated successfully";
    }

    public String updateUsername(String email , String username)
    {
        Seller seller = sellerDAO.findByEmail(email);
        if(seller == null){
            throw new SellerNotFound("Seller not exist");
        }

        seller.setUsername(username);
        sellerDAO.save(seller);
        return "Seller updated successfully";
    }


    public long CountNotification(String email) {

        Seller seller = sellerDAO.findByEmail(email);
        if (seller == null) {
            throw new SellerNotFound("Seller not exist");
        }

        List<NotificationBox> boxes =
                notificationBoxs.findBySeller_SellerId(seller.getSellerId());

        List<ResponseNotification> ans = new ArrayList<>();

        for (NotificationBox box : boxes) {
            if(box.isNotified()==true){
                continue;
            }
            ans.add(NotificationTransformer.NotificationtoResponseNotification(box.getNotificationCenter()));
        }
        long anss = ans.size();

        return anss;
    }


    public List<ResponseNotification> getallNotificatios(String email) {

        Seller seller = sellerDAO.findByEmail(email);
        if (seller == null) {
            throw new SellerNotFound("Seller not exist");
        }

        List<NotificationBox> boxes =
                notificationBoxs.findBySeller_SellerId(seller.getSellerId());

        List<ResponseNotification> ans = new ArrayList<>();

        for (NotificationBox box : boxes) {
            if(box.isNotified()==true){
                continue;
            }
            ans.add(NotificationTransformer.NotificationtoResponseNotification(box.getNotificationCenter()));
        }


        return ans;
    }


    public String AnswerNotification(String email , SellerNotified sellerNotified){
        Seller seller = sellerDAO.findByEmail(email);
        System.out.println(email);
        if(seller == null){
            throw new SellerNotFound("Seller not created ");

        }

        NotificationBox notificationBox = notificationBoxs.findByNotificationCenter_Id(sellerNotified.getNotificationId());
        if(notificationBox == null){
            throw new NotificationNotFound("Notification not found");
        }

        notificationBox.setNotified(true);
        notificationBoxs.save(notificationBox);
        return "Notification Launch";
    }


    public String deactivateUser(String email) {

        Seller seller = sellerDAO.findByEmail(email);
        if (seller == null) {
            throw new SellerNotFound("Seller not exist");
        }

        SellerDeactive deactivate = sellerDeactives
                .findBySeller_sellerId(seller.getSellerId());

        if (deactivate == null) {
            deactivate = SellerDeactive.builder()
                    .seller(seller)
                    .build();
        }

        deactivate.setActive(true);

        sellerDeactives.save(deactivate);

        return "User Deactivated Successfully";
    }

}
