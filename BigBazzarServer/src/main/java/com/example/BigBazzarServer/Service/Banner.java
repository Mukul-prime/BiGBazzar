package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.Model.Admins;
import com.example.BigBazzarServer.Model.Admins;
import com.example.BigBazzarServer.utlity.Enum.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String description;

    @Lob   // 🔥 better than columnDefinition
    private byte[] bannerImage;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admins admin;
}