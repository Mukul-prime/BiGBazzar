package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.Custome_methods.Geter;
import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
//import com.example.BigBazzarServer.DAO.ReviewDAO;
import com.example.BigBazzarServer.DTO.Request.*;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Exception.ProductNotFound;
import com.example.BigBazzarServer.Exception.SellerNotFound;
import com.example.BigBazzarServer.Model.*;
import com.example.BigBazzarServer.utlity.Transformers.ProductTransformers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    private final SellerDAO sellerDAO;

    @Autowired public  ProductTransformers productTransformers;
    @Autowired public Geter geter;


    @Transactional
    public String CreateProduct(ProductRequest productRequest, String email) throws IOException {
        Seller seller = sellerDAO.findByEmail(email);
        if (seller == null) {
            throw new SellerNotFound("Seller not created");
        }

        Product product = productTransformers.productRequestToProduct(productRequest);
        product.setSeller(seller);
        product.setProductImagesList(buildProductImages(product, productRequest.getBanner()));
        productDAO.save(product);
        return "Product loaded";
    }

    private List<ProductImages> buildProductImages(Product product, List<MultipartFile> files) throws IOException {
        List<ProductImages> productImagesList = new ArrayList<>();
        if (files == null || files.isEmpty()) {
            return productImagesList;
        }

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }

            productImagesList.add(ProductImages.builder()
                    .image(file.getBytes())
                    .product(product)
                    .build());
        }

        return productImagesList;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> data = productDAO.findAll(pageable);
        return data.map(productTransformers::productToProductResponse);
    }

    public String changeProductName(String email, ProductchangeNameRequest productchangeNameRequest) {
        Seller seller = sellerDAO.findByEmail(email);
        if (seller == null) {
            throw new SellerNotFound("Seller not created");
        }

        Optional<Product> product = productDAO.findById(productchangeNameRequest.getId());
        if (product.isEmpty()) {
            throw new ProductNotFound("Product not found");
        }
        product.get().setName(productchangeNameRequest.getName());
        productDAO.save(product.get());
        return "Product updated";
    }


    public String changePrice(String email, ProductChangePrice productChangePrice) {
        Seller seller = sellerDAO.findByEmail(email);
        if (seller == null) {
            throw new SellerNotFound("Seller not created");
        }
        Optional<Product> product = productDAO.findById(productChangePrice.getId());
        if (product.isEmpty()) {
            throw new ProductNotFound("Product not found");
        }
        Product product1 = product.get();
        product1.setPrice(productChangePrice.getNewPrice());
        productDAO.save(product1);
        return "Product price updated";

    }


    public String changeDescription(String email, ChangeProductDesciption changeProductDesciption) {
        Seller seller = sellerDAO.findByEmail(email);
        if (seller == null) {
            throw new SellerNotFound("Seller not created");
        }
        Optional<Product> product = productDAO.findById(changeProductDesciption.getId());
        if (product.isEmpty()) {
            throw new ProductNotFound("Product not found");
        }
        Product product1 = product.get();
        product1.setDescription(changeProductDesciption.getNewDescription());
        productDAO.save(product1);
        return "Product Description updated";

    }

//    public String chamge_image(String email, ProductChangeImages productChangeImages) throws IOException {
//        Seller seller = sellerDAO.findByEmail(email);
//        if (seller == null) {
//            throw new SellerNotFound("Seller not created");
//        }
//        Optional<Product> product = productDAO.findById(productChangeImages.getId());
//        if (product.isEmpty()) {
//            throw new ProductNotFound("Product not found");
//        }
//        Product product1 = product.get();
//        if (productChangeImages.getBanner() != null && !productChangeImages.getBanner().isEmpty()) {
//            product1.setImage(productChangeImages.getBanner().getBytes());
//        }
//        productDAO.save(product1);
//        return "Product Image updated";
//
//    }

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

    public List<ProductResponse> getexpireProduct() {

        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(10);

        List<Product> ans = productDAO.findExpiringProducts(today, futureDate);

        return ans.stream().map(product -> {

            long daysLeft = ChronoUnit.DAYS.between(today, product.getExpiryDate());
            double discount = calculateDiscount(daysLeft);

            double discountedPrice = product.getPrice() -
                    (product.getPrice() * discount / 100);

            String imageBase64 = null;




            LocalDate expireDate = product.getExpiryDate();
            String difference = String.valueOf(ChronoUnit.DAYS.between(today, expireDate));
            // build a product response for productresponse


            return ProductResponse.builder()
                    .id(product.getProductId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(discountedPrice)
                    .category(product.getCategory())
                    .bannerurl(geter.getproductimages(product))
                    .quantity(product.getQuantity())
                    .day(difference)
                    .build();

        }).toList();
    }

    LocalDate date = null;
    List<ProductResponse> cachedata = new ArrayList<>();

    public List<ProductResponse> getExpiredProducts() {

        LocalDate today = LocalDate.now();

        if(date!=null && date.isEqual(today)){
            return cachedata;
        }

        synchronized (this){
            if(date == null || !date.isEqual(today)){
                cachedata = getexpireProduct();
               date = today;

            }
        }
        return cachedata;

    }


}
