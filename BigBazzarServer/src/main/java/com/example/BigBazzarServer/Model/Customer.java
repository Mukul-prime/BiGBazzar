package com.example.BigBazzarServer.Model;

import com.example.BigBazzarServer.utlity.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"notificationList", "notificationBoxes", "customer", "seller"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(nullable = false)
    private String roles ;

    @Column(unique = true,nullable = false)
    private String username;
    @Column(unique = false,nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isVerified ;

    @Column(nullable = false)
    private String name;
    @Column(name = "\"year\"" ,nullable = false)
    private int year;

    @Column()
    private String otp;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String mobileNo;

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<Address> addressList = new ArrayList<>();


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<Reviews> reviewsList = new ArrayList<>();

    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationCenter> notificationList = new ArrayList<>();

}


