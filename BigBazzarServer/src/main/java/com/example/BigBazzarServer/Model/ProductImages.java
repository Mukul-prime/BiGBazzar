package com.example.BigBazzarServer.Model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "ProductS_images")
@Builder
public class ProductImages {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productImageId;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;






}
