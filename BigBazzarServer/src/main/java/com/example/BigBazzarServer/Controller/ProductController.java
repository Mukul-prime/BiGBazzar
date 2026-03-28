package com.example.BigBazzarServer.Controller;


import com.example.BigBazzarServer.DTO.Request.*;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Exception.ProductNotFound;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String welcomeProducts() {
        return "welcome to product";
    }
//create a product
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> CreateProduct(@ModelAttribute ProductRequest productRequest , Authentication authentication) {
        try {
            String email = authentication.getName();
            String response = productService.CreateProduct(productRequest , email);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (SellerNotFound | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    get all product
    @GetMapping("/Products")
    public ResponseEntity<?> GetAllProducts(Pageable pageable) {
        log.info("Getting all products");

        Page<ProductResponse> data = productService.getAllProducts(pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

//
//    @PostMapping("/bulk")
//    public ResponseEntity<String> addMultipleProducts(
//            @RequestBody List<ProductRequest> productRequests, Authentication authentication) {
//
//        String email = authentication.getName();
//        String response = productService.PutallDataProductonlyfordevelopment_mode(productRequests,email);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//
//    }

//    Change the product Name
    @PutMapping("/Productcn")
    public ResponseEntity<?> changeProductName(Authentication authentication , @RequestBody ProductchangeNameRequest productchangeNameRequest){
        log.info("Changing product name");
        try{
            String email =  authentication.getName();
            String response = productService.changeProductName(email, productchangeNameRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (SellerNotFound | ProductNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//change product price
    @PutMapping("/Productcp")
    public ResponseEntity<?> changeproductPrice(Authentication authentication , @RequestBody ProductChangePrice productChangePrice){
        log.info("Changing product price");
        try{
            String email =   authentication.getName();
            String response = productService.changePrice(email, productChangePrice);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (SellerNotFound | ProductNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


//    change product discription
    @PutMapping("/Productcd")
    public ResponseEntity<?> changeproductdescription(Authentication authentication , @RequestBody ChangeProductDesciption changeProductDesciption){
        log.info("Changing product price");
        try{
            String email =   authentication.getName();
            String response = productService.chnageDescritpon(email, changeProductDesciption);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (SellerNotFound | ProductNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

//change product image
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> changeproductImage(Authentication authentication , @ModelAttribute ProductChangeImages productChangeImages){
        log.info("Changing product price");
        try{
            String email =   authentication.getName();
            String response = productService.chamge_image(email, productChangeImages);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (SellerNotFound | ProductNotFound  | IOException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/days/{days}")
    public ResponseEntity<?> getexpireProduct(@PathVariable int days){
        log.info("Getting expire product");
        try{
            List<ProductResponse> data = productService.getexpireProduct(days);
            return new ResponseEntity<>(data, HttpStatus.OK);

        }
        catch ( Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }








}
