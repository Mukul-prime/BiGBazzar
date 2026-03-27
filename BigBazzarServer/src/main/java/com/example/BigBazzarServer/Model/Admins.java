package com.example.BigBazzarServer.Model;


import com.example.BigBazzarServer.Service.Banner;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Admin")
public class Admins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "admin")
    private List<City> cities;

    @OneToMany(mappedBy = "admin")
    private List<State> states;

    @OneToMany(mappedBy = "admin")
    private List<Banner> banners;
}