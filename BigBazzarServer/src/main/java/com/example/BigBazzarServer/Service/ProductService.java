package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.OrderRepository;
import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.DAO.ReviewDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.*;
import com.example.BigBazzarServer.DTO.Response.PageResponse;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Exception.ProductNotFound;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Model.*;
import com.example.BigBazzarServer.utlity.JwtService;
import com.example.BigBazzarServer.utlity.Transformers.ProductTransformers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.BigBazzarServer.DTO.Response.PageResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    private final SellerDAO sellerDAO;
    private final ReviewDAO reviewDAO;
    private final OrderRepository orderRepository;
    @Autowired
    private JwtService jwtService;

    @Transactional
    public String CreateProduct(ProductRequest productRequest ,String email ) throws IOException {
         Seller seller = sellerDAO.findByEmail(email);
         if(seller == null){
             throw new SellerNotFound("Seller not created");
         }
        Product product = ProductTransformers.productRequestToProduct(productRequest);
        product.setSeller(seller);
        if (productRequest.getBanner() != null && !productRequest.getBanner().isEmpty()) {
            product.setImage(productRequest.getBanner().getBytes());
        }

         productDAO.save(product);

        return "Product loaded";
    }



    public Page<ProductResponse> getAllProducts(Pageable pageable) {

        Page<Product> data = productDAO.findAll(pageable);

        return data.map(ProductTransformers::productToProductResponse);
    }

//    public String PutallDataProductonlyfordevelopment_mode(List<ProductRequest> productRequest,String token){
//        List<Product> productRequests = new ArrayList<>();
//        for(ProductRequest req : productRequest){
//
//            Seller seller = sellerDAO.findByEmail(token);
//            if(seller==null){
//                throw new SellerNotFound("Seller not found");
//            }
//
//
//
//            Product product = ProductTransformers.productRequestToProduct(req);
//            product.setSeller(seller);
//            productDAO.save(product);
//
//
//        }
//
//
//        return  "Products saved";
//    }



    public String changeProductName(String email , ProductchangeNameRequest productchangeNameRequest){
        Seller seller = sellerDAO.findByEmail(email);
        if(seller == null){
            throw new SellerNotFound("Seller not created");
        }

        Optional<Product> product = productDAO.findById(productchangeNameRequest.getId());
        if(product.isEmpty()){
            throw new ProductNotFound("Product not found");
        }
        product.get().setName(productchangeNameRequest.getName());
        productDAO.save(product.get());
        return "Product updated";
    }


    public String changePrice(String email , ProductChangePrice productChangePrice){
        Seller seller = sellerDAO.findByEmail(email);
        if(seller == null){
            throw new SellerNotFound("Seller not created");
        }
        Optional<Product> product = productDAO.findById(productChangePrice.getId());
        if(product.isEmpty()){
            throw new ProductNotFound("Product not found");
        }
        Product product1 = product.get();
        product1.setPrice(productChangePrice.getNewPrice());
        productDAO.save(product1);
        return "Product price updated";

    }


    public String chnageDescritpon(String email , ChangeProductDesciption changeProductDesciption){
        Seller seller = sellerDAO.findByEmail(email);
        if(seller == null){
            throw new SellerNotFound("Seller not created");
        }
        Optional<Product> product = productDAO.findById(changeProductDesciption.getId());
        if(product.isEmpty()){
            throw new ProductNotFound("Product not found");
        }
        Product product1 = product.get();
        product1.setDescription(changeProductDesciption.getNewDescription());
        productDAO.save(product1);
        return "Product Description updated";

    }

    public String chamge_image(String email , ProductChangeImages productChangeImages) throws IOException {
        Seller seller = sellerDAO.findByEmail(email);
        if(seller == null){
            throw new SellerNotFound("Seller not created");
        }
        Optional<Product> product = productDAO.findById(productChangeImages.getId());
        if(product.isEmpty()){
            throw new ProductNotFound("Product not found");
        }
        Product product1 = product.get();
        if (productChangeImages.getBanner() != null && !productChangeImages.getBanner().isEmpty()) {
            product1.setImage(productChangeImages.getBanner().getBytes());
        }
        productDAO.save(product1);
        return "Product Image updated";

    }

/*
* if day is 0 to 1 it returns high discount
* if days 0 to 5 middle discount
* if day 0 to 10 low discount
* else return 5% discount on every product
*
*
* */
    public double calculateDiscount(long daysLeft) {
        if (daysLeft <= 2) return 40;
        else if (daysLeft <= 5) return 20;
        else if (daysLeft <= 10) return 10;
        else return 5;
    }

    public List<ProductResponse> getexpireProduct(int days){

        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(days);

        List<Product> ans = productDAO.findExpiringProducts(today, futureDate);

        return ans.stream().map(product -> {

            long daysLeft = ChronoUnit.DAYS.between(today, product.getExpiryDate());
            double discount = calculateDiscount(daysLeft);

            double discountedPrice = product.getPrice() -
                    (product.getPrice() * discount / 100);

            String imageBase64 = null;
//this is for image url to verify it if image is null they set null or not null is set is an image on data
            if (product.getImage() != null) {
                imageBase64 = Base64.getEncoder().encodeToString(product.getImage());
            }


//            LocalDate today = LocalDate.now();
            LocalDate expireDate = product.getExpiryDate();
            String difference = String.valueOf(ChronoUnit.DAYS.between(today, expireDate));
// build a product response for productresponse


            return ProductResponse.builder()
                    .id(product.getProductId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(discountedPrice)
                    .category(product.getCategory())
                    .bannerurl(imageBase64)
                    .day(difference)
                    .build();

        }).toList();
    }











}
