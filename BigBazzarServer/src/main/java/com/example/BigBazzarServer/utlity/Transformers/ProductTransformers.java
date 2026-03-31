package com.example.BigBazzarServer.utlity.Transformers;

import com.example.BigBazzarServer.Custome_methods.Geter;
import com.example.BigBazzarServer.DAO.ProductImageDAO;
import com.example.BigBazzarServer.DTO.Request.ProductRequest;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.DTO.Response.SellerResponse;
import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.Model.ProductImages;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Component
public class ProductTransformers {

    @Autowired public Geter geter;



    public  Product productRequestToProduct(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .expiryDate(LocalDate.parse(productRequest.getBirthDateBody()))
                .quantity(productRequest.getQuantity())
                .build();
    }


    public    ProductResponse productToProductResponse(Product product){
      List<String> a = geter.getproductimages(product);

        return ProductResponse.builder()
                .id(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .bannerurl(a)
                .quantity(product.getQuantity())
                .build();
    }
//    public static List<ProductResponse> productListToProductResponse(
//            List<Product> products) {
//
//        return products.stream()
//                .map(ProductTransformers::productToProductResponse)
//                .collect(Collectors.toList());
//    }



    public static List<ProductResponse> productToProductResponsev2(
            List<Product> products) {

        List<ProductResponse> responseList = new ArrayList<>();

        for (Product product : products) {
            ProductResponse response = new ProductResponse();
//            response.setId(product.getId());
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            response.setDescription(product.getDescription());
            response.setCategory(product.getCategory());

            responseList.add(response);
        }

        return responseList;
    }
}
