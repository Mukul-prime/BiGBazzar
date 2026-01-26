package com.example.BigBazzarServer.Model;

//import com.example.BigBazzarServer.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;

//import java.util.List;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId; // primary key, auto_increment ✅

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY) // optional: lazy fetch
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY) // optional: lazy fetch
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}


