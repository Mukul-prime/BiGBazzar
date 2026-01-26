package com.example.BigBazzarServer.utlity.Transformers;

import com.example.BigBazzarServer.DTO.Request.ProductRequest;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.DTO.Response.SellerResponse;
import com.example.BigBazzarServer.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductTransformers {
    public static Product productRequestToProduct(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .build();
    }

    public static ProductResponse productToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())

                .build();
    }
    public static List<ProductResponse> productListToProductResponse(
            List<Product> products) {

        return products.stream()
                .map(ProductTransformers::productToProductResponse)
                .collect(Collectors.toList());
    }

    public static List<ProductResponse> productListToProductResponses(
            List<Product> products) {

        return products.stream()
                .map(ProductTransformers::productToProductResponse)
                .collect(Collectors.toList());
    }

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
