package com.example.BigBazzarServer.ConfigurationSecurity;

import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
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

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // ✅ 1. Seller check
        Seller seller = sellerDAO.findByUsername(username);
        if (seller != null) {
            return new User(
                    seller.getUsername(),
                    seller.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_SELLER"))
            );
        }

        // ✅ 2. Customer check
        Customer customer = customerDAO.findByUsername(username);
        if (customer != null) {
            return new User(
                    customer.getUsername(),
                    customer.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
