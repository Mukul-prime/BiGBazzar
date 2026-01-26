package com.example.BigBazzarServer.Controller;

import com.example.BigBazzarServer.DTO.Request.ReviewRequest;
import com.example.BigBazzarServer.DTO.Response.ReviewResponse;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Service.ReviewService;
import com.example.BigBazzarServer.utlity.Transformers.SellerTransformers;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public String welcome_Reviews(){
        return "Welcome to Review!";
    }
    @PostMapping
    public ResponseEntity<?> CreateReview( @RequestBody ReviewRequest reviewRequest){
        log.info("Creating Review");
            try {
                ReviewResponse response = reviewService.CreateReview(reviewRequest);
                return new ResponseEntity<>(response, HttpStatus.CREATED);

            }
            catch (CustomerNotFound e){
                return new ResponseEntity<>(e.getCause(),HttpStatus.NOT_FOUND);
            }
    }

}
