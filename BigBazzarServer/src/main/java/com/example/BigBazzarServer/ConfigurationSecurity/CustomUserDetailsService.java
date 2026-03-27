package com.example.BigBazzarServer.ConfigurationSecurity;

import com.example.BigBazzarServer.DAO.AdminDAO;
import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.Model.Admins;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SellerDAO sellerDAO;
    private final CustomerDAO customerDAO;
    private final AdminDAO adminDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {



        Seller seller = sellerDAO.findByEmail(email);
        if (seller != null) {

            return new User(
                    seller.getEmail(),
                    seller.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_SELLER"))
            );
        }

        Customer customer = customerDAO.findByEmail(email);
        if (customer != null) {

            return new User(
                    customer.getEmail(),
                    customer.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        }

        Admins admin = adminDAO.findByEmail(email);
        if (admin != null) {

            return new User(
                    admin.getEmail(),
                    admin.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}