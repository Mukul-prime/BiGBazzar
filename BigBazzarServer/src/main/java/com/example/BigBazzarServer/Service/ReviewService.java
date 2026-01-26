package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.DAO.ReviewDAO;
import com.example.BigBazzarServer.DTO.Request.ReviewRequest;
import com.example.BigBazzarServer.DTO.Response.ReviewResponse;
import com.example.BigBazzarServer.Exception.CustomerNotCreated;
import com.example.BigBazzarServer.Exception.ProductNotFound;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.Model.Reviews;
import com.example.BigBazzarServer.utlity.Transformers.ReviewTransformers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDAO reviewDAO;
    private final CustomerDAO customerDAO;
    private final ProductDAO productDAO;

    public ReviewResponse CreateReview(ReviewRequest reviewRequest){
        Customer customer = customerDAO.findById(reviewRequest.getCustomerId()).orElseThrow(
                ()-> new CustomerNotCreated("Customer not created"));

        Product product = productDAO.findById(reviewRequest.getProductid()).orElseThrow(()->
                new ProductNotFound("Not Founded"));

        Reviews reviews = ReviewTransformers.reviewRequestToReviews(reviewRequest);
        reviews.setCustomer(customer);
        reviews.setProduct(product);

        Reviews savedReviews = reviewDAO.save(reviews);

        return ReviewTransformers.reviewtoReviewsResponse(savedReviews);
    }
}
