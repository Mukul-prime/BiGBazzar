package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.AdminDAO;
import com.example.BigBazzarServer.DTO.Request.OrderChangeStatus;
import com.example.BigBazzarServer.DTO.Request.OrderEntityRequest;
import com.example.BigBazzarServer.DTO.Response.OrderEntityResponse;
import com.example.BigBazzarServer.DTO.Response.OrderItemResponse;
import com.example.BigBazzarServer.Exception.*;
import com.example.BigBazzarServer.Model.*;
import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.OrderRepository;
import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.utlity.Enum.Status;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerDAO customerRepository;
    private final ProductDAO productRepository;
    private final JavaMailSender mailSender;
    private final AdminDAO adminDAO;


    @Transactional
    public OrderEntityResponse createOrder(OrderEntityRequest request, String email) {

        // 1 Customer fetch
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null){
            throw new CustomerNotFound("Please login it ");
        }

        // 2 OrderEntity banaye
        OrderEntity order = OrderEntity.builder()
                .customer(customer)
                .status(request.getStatus())
                .build();


        List<OrderItem> items = request.getOrderItems().stream().map(itemReq -> {

            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ProductNotFound("Product not found"));

            return OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .order(order)
                    .build();

        }).toList();

        order.setOrderItems(items);


        OrderEntity savedOrder = orderRepository.save(order);


        List<OrderItemResponse> responseItems = savedOrder.getOrderItems().stream().map(item ->
                OrderItemResponse.builder()
                        .orderItemId(item.getOrderItemId())
                        .productId(item.getProduct().getProductId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getProduct().getPrice())
                        .build()
        ).toList();
        Customer c =  savedOrder.getCustomer();
        sendEmail(c, responseItems);


        return OrderEntityResponse.builder()
                .orderId(savedOrder.getOrderId())
                .createdAt(savedOrder.getCreatedAt())
                .status(savedOrder.getStatus())
                .customerId(customer.getCustomerId())
                .customerName(customer.getName())
                .orderItems(responseItems)
                .build();
    }

    private void sendEmail(Customer customer, List<OrderItemResponse> responseItems) {

        StringBuilder text = new StringBuilder();
        text.append("Hello ").append(customer.getName()).append(",\n\n");
        text.append("Your order has been created successfully.\n\n");
        text.append("Order Details:\n");

        double total = 0;

        for (OrderItemResponse item : responseItems) {
            text.append("- ")
                    .append(item.getProductName())
                    .append(" | Qty: ")
                    .append(item.getQuantity())
                    .append(" | Price: ")
                    .append(item.getPrice())
                    .append("\n");

            total += item.getPrice() * item.getQuantity();
        }

        text.append("\nTotal Amount: ").append(total);
        text.append("\n\nThank you for shopping with us!");
        text.append("\nBigBazzar Team");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("Mukulvats31124@gmail.com");
        mailMessage.setTo(customer.getEmail());
        mailMessage.setSubject("Order Created Successfully");
        mailMessage.setText(text.toString());
        mailSender.send(mailMessage);


    }

    public String OrderCanceled(int orderId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepository.findByEmail(email);
       if(customer == null){
           throw new CustomerNotFound("Customer not found");
       }
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

       Enum<Status> ans = order.getStatus();
       if(ans ==  Status.CANCELLED){
           throw new OrderAlreadyCanceled("Order already canceled");
       }

        orderRepository.delete(order);
        return "Order has been canceled successfully";
    }

    public String changetheStatus(String email, OrderChangeStatus changeStatus){
        Admins admins = adminDAO.findByEmail(email);
        if(admins == null){
            throw new AdminNotfound("please login admin");
        }

        Optional<OrderEntity> order = orderRepository.findById(changeStatus.getOrderid());
        if(order.isEmpty()){
            throw new OrderNotFound("Order not created by user");

        }
        OrderEntity order1 = order.get();
        order1.setStatus(changeStatus.getStatus());
        orderRepository.save(order1);
        return "Order has been updated successfully";



    }


}

