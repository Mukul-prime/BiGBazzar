package com.example.BigBazzarServer.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder



@Table(name = "notification_box")
@ToString(exclude = {"notificationList", "notificationBoxes", "customer", "seller"})
public class NotificationBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_center_id")
    @JsonIgnore
    private NotificationCenter notificationCenter;

    @Column(nullable = false)
    private boolean notified;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnore
    private Seller seller;




}
