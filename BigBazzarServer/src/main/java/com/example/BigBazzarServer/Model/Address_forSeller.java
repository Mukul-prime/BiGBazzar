
package com.example.BigBazzarServer.Model;


import jakarta.persistence.*;
        import lombok.*;
@Entity
@Table(name = "addressForSeller")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Address_forSeller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    @Column(nullable = false)
    private String houseNo;
    @Column(length = 6,nullable = false)
    private String pinCode;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
