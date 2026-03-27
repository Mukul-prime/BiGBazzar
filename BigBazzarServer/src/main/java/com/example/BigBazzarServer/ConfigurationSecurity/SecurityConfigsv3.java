package com.example.BigBazzarServer.ConfigurationSecurity;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigsv3 {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
//                .csrf(csrf -> csrf
//                        .csrfTokenRepository(
//                                CookieCsrfTokenRepository.withHttpOnlyFalse()
//                        )
//                        .ignoringRequestMatchers(
//                                "/api/v1/Customer/signup",
//                                "/api/v1/auth/**",
//                                "/api/v1/Customer/Verified",
//                                "/api/v1/Seller/signup",
//                                "/api/v1/Seller/IsVerified",
//                                "/api/v1/Product/Products"
//
//                        )
//                )

                .authorizeHttpRequests(auth -> auth


                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                                .requestMatchers(
                                        "/api/v1/Home",
                                        "/api/v1/Customer"


                                ).permitAll()

                                // PUBLIC APIs
                                .requestMatchers(
                                        "/api/v1/auth/**",
                                        "/api/v1/Seller/signup",
                                        "/api/v1/Customer/signup",
                                        "/api/v1/Product/Products",
                                        "/api/v1/Product",
                                        "/api/v1/auth/seller/login",

                                        "/api/v1/Customer/name/**",
                                        "/api/v1/auth/customer/login",
                                        "/api/v1/Review/",
                                        "/api/v1/auth/admin/login",
                                        "/api/v1/Admin/Admin",
                                        "/api/v1/Product/days/**"



                                ).permitAll()

                                // SELLER

                                .requestMatchers("/api/v1/Seller/**").hasRole("SELLER")
                                // CUSTOMER
//                                .requestMatchers("/api/v1/order/**")
//                                .hasRole("CUSTOMER")
                                .requestMatchers("/api/v1/City/**").hasRole("ADMIN")
//                        .requestMatchers("/api/v1/City/City").hasRole("ADMIN")

                                .requestMatchers("/api/v1/State/**").hasRole("ADMIN")



                                .requestMatchers("/api/v1/Customer/**").hasAnyRole("ADMIN", "CUSTOMER")
                                .requestMatchers("/api/v1/Seller/**").hasAnyRole("ADMIN", "SELLER")
                                .requestMatchers("/api/v1/order/**").hasAnyRole("ADMIN", "SELLER","CUSTOMER")
                                .requestMatchers("/api/v1/Product/**").hasAnyRole("ADMIN", "SELLER")
                                .requestMatchers("/api/v1/Admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/Notification/").hasAnyRole("ADMIN","CUSTOMER")
                                .requestMatchers("/api/v1/Review/Review").hasAnyRole("CUSTOMER", "ADMIN")
                                .anyRequest().authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        System.out.println(provider);
        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService userDetailsService
    ) throws Exception {

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return builder.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(
                "http://localhost:5500",
                "http://127.0.0.1:5500",
                "http://localhost:63342",
                "http://localhost:5173",
                "http://localhost:63343"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}

