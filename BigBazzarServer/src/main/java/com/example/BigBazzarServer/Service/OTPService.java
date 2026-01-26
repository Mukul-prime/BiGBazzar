package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.VerifiedOTP;
import com.example.BigBazzarServer.DTO.Request.VerifiedOTPseller;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final JavaMailSender mailSender;
    private final CustomerDAO customerDAO;
    private final SellerDAO sellerDAO;

    public boolean checker( String otp , String generateOtp){
        if(otp.equals(generateOtp)){
            return true;
        }
        return false;

    }

    public  String IsVerfiedPlease(VerifiedOTP verifiedOTP){
        Customer customer = customerDAO.findByUsername(verifiedOTP.getUsername());
        System.out.println(customer);
        if(customer == null){

            throw new CustomerNotFound("Not exist");

        }

        boolean c = checker(verifiedOTP.getOtp() ,customer.getOtp() );
        if(c){
            customer.setVerified(true);
            customer.setOtp(null);
            customerDAO.save(customer);
            return "Your are verified";
        }
        return "your are not verified";


    }



    public  String IVerfiedPlease(VerifiedOTPseller verifiedOTPseller){
        Seller customer = sellerDAO.findByUsername(verifiedOTPseller.getUsername());
        System.out.println(customer);
        if(customer == null){

            throw new CustomerNotFound("Not exist");

        }

        boolean c = checker(verifiedOTPseller.getOtp() ,customer.getOtp() );
        if(c){
            customer.setVerifiedis(true);
            customer.setOtp(null);
            sellerDAO.save(customer);
            return "Your are verified";
        }
        return "your are not verified";


    }
    void sendEmail(Customer customer, String OneTimePassword) {

        StringBuilder text = new StringBuilder();
        text.append("Your OTP off Bigbazzar application is "+OneTimePassword+"\n");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("Mukulvats31124@gmail.com");
        mailMessage.setTo(customer.getEmail());
        mailMessage.setSubject("One time password is : ");
        mailMessage.setText(text.toString());
        mailSender.send(mailMessage);


    }

    void sellersendEmail(Seller seller, String OneTimePassword) {

        StringBuilder text = new StringBuilder();
        text.append("Your OTP off Bigbazzar application is "+OneTimePassword+"\n");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("Mukulvats31124@gmail.com");
        mailMessage.setTo(seller.getEmail());
        mailMessage.setSubject("One time password is : ");
        mailMessage.setText(text.toString());
        mailSender.send(mailMessage);


    }
}
