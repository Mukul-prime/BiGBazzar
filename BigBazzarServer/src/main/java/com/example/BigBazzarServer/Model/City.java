package com.example.BigBazzarServer.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cityId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_code")
    private State state;

    @ManyToOne
    @JoinColumn(name = "admin_Id")
    private Admins admin;
}
