package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DTO.Request.OrderEntityRequest;
import com.example.BigBazzarServer.DTO.Response.OrderEntityResponse;
import com.example.BigBazzarServer.DTO.Response.OrderItemResponse;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Exception.OrderAlreadyCanceled;
import com.example.BigBazzarServer.Exception.ProductNotFound;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.OrderEntity;
import com.example.BigBazzarServer.Model.OrderItem;
import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.OrderRepository;
import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.utlity.Enum.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerDAO customerRepository;
    private final ProductDAO productRepository;
    private final JavaMailSender mailSender;


    @Transactional
    public OrderEntityResponse createOrder(OrderEntityRequest request) {

        // 1️⃣ Customer fetch
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFound("Customer not found"));

        // 2️⃣ OrderEntity banaye
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
                    .order(order) // 🔥 important, owner side set
                    .build();

        }).toList();

        order.setOrderItems(items);

        // 4️⃣ Save order (cascade saves items automatically)
        OrderEntity savedOrder = orderRepository.save(order);

        // 5️⃣ ResponseDTO map karna
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


}

