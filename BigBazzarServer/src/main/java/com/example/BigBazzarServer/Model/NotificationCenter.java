package com.example.BigBazzarServer.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "NotificationCenter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"notificationList", "notificationBoxes", "customer", "seller"})
public class NotificationCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Lob
    private  byte[] productImage;
    @Column(nullable = false)
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "notificationCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationBox> notificationBoxes = new ArrayList<>();


    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    protected LocalDateTime createdDate;
}
