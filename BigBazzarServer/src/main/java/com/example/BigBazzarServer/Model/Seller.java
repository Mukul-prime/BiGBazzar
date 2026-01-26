package com.example.BigBazzarServer.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sellers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sellerId;
    @Column(nullable = false)
    private String roles ;

    @Column(nullable = false)
    private Boolean verifiedis;
    @Column
    private String otp;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(unique = false,nullable = false)
    private String password;

    private String name;
    private int age;

    @Column(nullable = false, unique = true,length = 10)
    private String gst;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "seller",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Address_forSeller> Address_forSeller;

    @OneToMany(mappedBy = "seller",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Product> products;
}

