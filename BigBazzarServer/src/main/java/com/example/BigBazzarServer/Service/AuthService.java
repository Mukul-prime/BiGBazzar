package com.example.BigBazzarServer.Service;




import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.LoginDTO;
import com.example.BigBazzarServer.DTO.Response.LoginResponseDTO;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.utlity.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SellerDAO sellerDAO;
    private final CustomerDAO customerDAO;

    public LoginResponseDTO sellerLogin(LoginDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        Seller seller = sellerDAO.findByUsername(dto.getUsername());

        String token = jwtService.generateToken(
                seller.getUsername(),
                seller.getRoles()   // ROLE_SELLER
        );


        return new LoginResponseDTO(token, seller.getUsername(), seller.getRoles());
    }

    public String customerLogin(LoginDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        Customer customer =
                customerDAO.findByUsername(dto.getUsername());

        String token = jwtService.generateToken(
                customer.getUsername(),
                customer.getRoles()
        );

        return token;
    }
}
