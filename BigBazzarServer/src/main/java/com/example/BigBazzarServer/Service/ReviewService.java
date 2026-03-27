package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.DAO.ReviewDAO;
import com.example.BigBazzarServer.DTO.Request.EditReview;
import com.example.BigBazzarServer.DTO.Request.ReviewRequest;
import com.example.BigBazzarServer.DTO.Response.ReviewResponse;
import com.example.BigBazzarServer.Exception.CustomerNotCreated;
import com.example.BigBazzarServer.Exception.ProductNotFound;
import com.example.BigBazzarServer.Exception.ReviewNotFound;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.Model.Reviews;
import com.example.BigBazzarServer.utlity.Transformers.ReviewTransformers;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDAO reviewDAO;
    private final CustomerDAO customerDAO;
    private final ProductDAO productDAO;

    public ReviewResponse CreateReview(ReviewRequest reviewRequest ,  String emails ){
        Customer customer = customerDAO.findByEmail(emails);
        if(customer == null){
            throw new CustomerNotCreated("Customer Not found");
        }
        System.out.println();

        Product product = productDAO.findById(reviewRequest.getProductid()).orElseThrow(()->
                new ProductNotFound("Not Founded"));

        Reviews reviews = ReviewTransformers.reviewRequestToReviews(reviewRequest);
        reviews.setCustomer(customer);
        reviews.setProduct(product);

        Reviews savedReviews = reviewDAO.save(reviews);

        return ReviewTransformers.reviewtoReviewsResponse(savedReviews);
    }

    public String editReview(EditReview editReview, String emails ){
        Customer customer = customerDAO.findByEmail(emails);
        if(customer == null){
            throw new CustomerNotCreated("Customer Not found");

        }

        Optional<Reviews> reviews  = reviewDAO.findById(editReview.reviewid);
        if(reviews == null){
            throw new  ReviewNotFound("Not Founded");
        }

        Reviews review = reviews.get();
        review.setReviewText(editReview.getEdittext());
        reviewDAO.save(review);
        return "Edited";
    }


    public String Deletereview(int id , String emails){
        Customer customer = customerDAO.findByEmail(emails);
        if(customer == null) {
            throw new CustomerNotCreated("Customer Not found");

        }

        Optional<Reviews> reviews = reviewDAO.findById(id);
        if(reviews == null){
            throw new ReviewNotFound("Not Founded");
        }
        reviewDAO.deleteById(id);

        return "Deleted";

    }
}
