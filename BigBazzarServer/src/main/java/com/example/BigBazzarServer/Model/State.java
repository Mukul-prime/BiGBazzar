package com.example.BigBazzarServer.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.List;
@Entity
@Table(name = "states")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stateId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false , unique = true,length = 3)
    private String stateCode;

    @OneToMany(mappedBy = "state",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<City> cityList;
}
