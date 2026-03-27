package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.AdminDAO;
import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.LoginDTO;
import com.example.BigBazzarServer.DTO.Response.LoginResponseDTO;
import com.example.BigBazzarServer.Model.Admins;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.utlity.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerDAO customerDAO;
    private final SellerDAO sellerDAO;
    private final AdminDAO adminDAO;


    private void authenticate(String email, String password) {
        try {
            System.out.println(email+" "+password);
            authenticationManager.authenticate(


                    new UsernamePasswordAuthenticationToken(email,password)
            );
        } catch (Exception e) {
            e.printStackTrace();   // IMPORTANT
            throw new BadCredentialsException("Invalid credentials");
        }
    }


    public LoginResponseDTO customerLogin(LoginDTO dto) {

        System.out.println(dto.getEmail()+" "+ dto.getPassword());
        String email = dto.getEmail().toLowerCase();

        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new BadCredentialsException("Customer not found");
        }





        authenticate(email, dto.getPassword());

        String token = jwtService.generateToken(
                customer.getRoles(),
                customer.getEmail()
        );

        return new LoginResponseDTO(
                token,
                customer.getUsername(),
                customer.getRoles()
        );
    }

    public LoginResponseDTO sellerLogin(LoginDTO dto) {

        authenticate(dto.getEmail(), dto.getPassword());

        Seller seller = sellerDAO.findByEmail(dto.getEmail());
        if (seller == null) {
            throw new BadCredentialsException("Seller not found");
        }

        String token = jwtService.generateToken(
                seller.getRoles(),
                seller.getEmail()
        );

        return new LoginResponseDTO(
                token,
                seller.getUsername(), // ya username jo bhi hai
                seller.getRoles()
        );
    }


    public LoginResponseDTO adminLogin(LoginDTO dto) {

        authenticate(dto.getEmail(), dto.getPassword());

        Admins admin = adminDAO.findByEmail(dto.getEmail());
        if (admin == null) {
            throw new BadCredentialsException("Admin not found");
        }

        String token = jwtService.generateToken(
                admin.getRole(),
                admin.getEmail()
        );

        return new LoginResponseDTO(
                token,
                admin.getUsername(),
                admin.getRole()
        );
    }
}