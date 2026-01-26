//package com.example.BigBazzarServer.ConfigurationSecurity;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfigs {
//
//    private final CustomerUserDetailsService customerUserDetailsService;
//
//    private final SellerUserDetailsService sellerUserDetailsService;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                // ✅ CORS ENABLE
//                .cors(Customizer.withDefaults())
//
////                 ✅ CSRF (cookie based)
////                .csrf(csrf -> csrf
////                        .csrfTokenRepository(
////                                CookieCsrfTokenRepository.withHttpOnlyFalse()
////                        )
////                        .ignoringRequestMatchers(
////                                "/api/v1/Customer/signup",
////                                "/api/v1/auth/**",
////                                "/api/v1/Customer/Verified",
////                                "/api/v1/Seller/signup",
////                                "/api/v1/Seller/IsVerified"
////
////                        )
////                )
//                .csrf(csrf -> csrf.disable())
//
//
//                // ✅ Authorization
//                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers(
//                                        "/swagger-ui/**",
//                                        "/v3/api-docs/**",
//                                        "/swagger-ui.html"
//                                ).permitAll()
//                        .requestMatchers("/api/v1/Product/").permitAll()
//                                .requestMatchers("/api/v1/auth/**").permitAll()
//                                .requestMatchers("/api/v1/Home").permitAll()
//                                .requestMatchers("/api/v1/Customer").permitAll()
//                                .requestMatchers("/api/v1/Customer/signup").permitAll()
//                                .requestMatchers("/api/v1/Customer/Verified").permitAll()
//                                .requestMatchers("/api/v1/Seller/signup").permitAll()
//
//                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/v1/customer/**").hasRole("CUSTOMER")
//                        .requestMatchers("/api/v1/Seller/**").hasRole("SELLER")
//                        .requestMatchers("/api/v1/Product/**").hasRole("SELLER")
//                                .anyRequest().authenticated()
//                )
//
//                // ✅ Stateless (JWT ready)
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        return http.build();
//    }
//
//    // ✅ CORS CONFIG (MAIN FIX)
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(List.of(
//                "http://localhost:5500",
//                "http://127.0.0.1:5500",
//                "http://localhost:63342",
//                "http://localhost:5173",
//                "http://localhost:63343"
//        ));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//
//
//
//
//
////     🔐 REQUIRED for Login
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder builder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//
//       builder(CustomUserDetailsService)
//
//        return builder.build();
//    }
//
//
//    // 🔐 Password Encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
