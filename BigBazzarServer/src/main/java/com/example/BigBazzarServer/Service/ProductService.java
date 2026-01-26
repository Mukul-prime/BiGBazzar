package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.ProductRequest;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.utlity.JwtService;
import com.example.BigBazzarServer.utlity.Transformers.ProductTransformers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    private final SellerDAO sellerDAO;
    @Autowired
    private JwtService jwtService;

    public String CreateProduct(ProductRequest productRequest ) {
         Seller seller = sellerDAO.findById(productRequest.getSellerID()).orElseThrow(()->
                 new SellerNotFound("Invalid Seller"));




        Product product = ProductTransformers.productRequestToProduct(productRequest);
        product.setSeller(seller);

        Product product1 = productDAO.save(product);

        return "Product loaded";
    }



    public List<ProductResponse> getAllProducts() {

        List<Product> data = productDAO.findAll();

        return ProductTransformers.productListToProductResponse(data);
    }

    public String PutallDataProductonlyfordevelopment_mode(List<ProductRequest> productRequest,String token){
        List<Product> productRequests = new ArrayList<>();
        for(ProductRequest req : productRequest){
            String username = jwtService.extractUsername(token);
            Seller seller = sellerDAO.findByUsername(username);
            if(seller==null){
                throw new SellerNotFound("Seller not found");
            }



            Product product = ProductTransformers.productRequestToProduct(req);
            product.setSeller(seller);
            productDAO.save(product);


        }


        return  "Products saved";
    }





}
