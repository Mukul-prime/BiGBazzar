package com.example.BigBazzarServer.utlity.Transformers;

import com.example.BigBazzarServer.DTO.Request.ReviewRequest;
import com.example.BigBazzarServer.DTO.Response.ReviewResponse;
import com.example.BigBazzarServer.Model.Reviews;

public class ReviewTransformers {
    public static Reviews reviewRequestToReviews(ReviewRequest reviewRequest){
        return Reviews.builder()
                .reviewText(reviewRequest.getReviewText())
                .build();
    }
    public static ReviewResponse reviewtoReviewsResponse(Reviews reviews){
        return ReviewResponse.builder()
                .reviewText(reviews.getReviewText())
                .date(reviews.getCreatedAt())
                .product(ProductTransformers.productToProductResponse(reviews.getProduct()))
                .customer(CustomerTransformer.customerToCustomerResponse(reviews.getCustomer()))
                .build();
    }

}
