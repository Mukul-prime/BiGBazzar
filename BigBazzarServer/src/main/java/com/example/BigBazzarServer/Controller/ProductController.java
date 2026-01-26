package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.ProductRequest;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String welcomeProducts() {
        return "welcome to product";
    }

    @PostMapping("/Product")
    public ResponseEntity<?> CreateProduct(@RequestBody ProductRequest productRequest) {
        try {
            String response = productService.CreateProduct(productRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (SellerNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/Products")
    public ResponseEntity<?> GetAllProducts() {
        log.info("Getting all products");
        List<ProductResponse> data = productService.getAllProducts();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    @PostMapping("/bulk")
    public ResponseEntity<String> addMultipleProducts(
            @RequestBody List<ProductRequest> productRequests,@RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        String tokens = token
                .replace("Bearer ", "")
                .trim();
        String response = productService.PutallDataProductonlyfordevelopment_mode(productRequests,tokens);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
