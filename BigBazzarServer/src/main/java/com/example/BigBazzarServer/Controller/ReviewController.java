package com.example.BigBazzarServer.Controller;

import com.example.BigBazzarServer.DTO.Request.EditReview;
import com.example.BigBazzarServer.DTO.Request.ReviewRequest;
import com.example.BigBazzarServer.DTO.Response.ReviewResponse;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Exception.ReviewNotFound;
import com.example.BigBazzarServer.Service.ReviewService;
import com.example.BigBazzarServer.utlity.Transformers.SellerTransformers;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/")
    public String welcome_Reviews() {
        return "Welcome to Review!";
    }
// create a review
    @PostMapping("/Review")
    public ResponseEntity<?> CreateReview(@RequestBody ReviewRequest reviewRequest, Authentication authentication) {
        log.info("Creating Review");
        try {
            System.out.println(authentication.getName() + "  " + reviewRequest.getReviewText() + " " + reviewRequest.getProductid());
            String email = authentication.getName();
            ReviewResponse response = reviewService.CreateReview(reviewRequest, email);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (CustomerNotFound e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.NOT_FOUND);
        }


    }


//    edit review
    @PutMapping("/Review")
    public ResponseEntity<String> EditReview(@RequestBody EditReview editReview , Authentication authentication) {
        log.info("Editing Review");
        try {
            String email  = authentication.getName();
            String resposne  = reviewService.editReview(editReview, email);
            return new ResponseEntity<>(resposne, HttpStatus.OK);
        }
        catch (CustomerNotFound | ReviewNotFound e){
            return  new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }
//delete a reveiw

    @DeleteMapping("/Review")
    public ResponseEntity<String> DeleteReview(@RequestParam int id , Authentication authentication) {
        log.info("Deleting Review");
        try{
            String email = authentication.getName();
            String response = reviewService.Deletereview(id, email);
            return new ResponseEntity<>(response, HttpStatus.OK);



        }
        catch (CustomerNotFound | ReviewNotFound e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }

}
