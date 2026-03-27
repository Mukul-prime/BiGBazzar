package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.OrderRepository;
import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.DAO.ReviewDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.*;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Exception.ProductNotFound;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Model.*;
import com.example.BigBazzarServer.utlity.JwtService;
import com.example.BigBazzarServer.utlity.Transformers.ProductTransformers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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

        Product product1 = productDAO.save(product);

        return "Product loaded";
    }



    public List<ProductResponse> getAllProducts() {

        List<Product> data = productDAO.findAll();

        return ProductTransformers.productListToProductResponse(data);
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

            if (product.getImage() != null) {
                imageBase64 = Base64.getEncoder().encodeToString(product.getImage());
            }


            return ProductResponse.builder()
                    .id(product.getProductId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(discountedPrice)
                    .category(product.getCategory())
                    .bannerurl(imageBase64)
                    .build();

        }).toList();
    }











}
