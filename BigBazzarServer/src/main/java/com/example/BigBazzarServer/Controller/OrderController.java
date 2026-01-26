package com.example.BigBazzarServer.Controller;

import com.example.BigBazzarServer.DTO.Request.OrderEntityRequest;
import com.example.BigBazzarServer.DTO.Response.OrderEntityResponse;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Exception.ProductNotFound;
import com.example.BigBazzarServer.Service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String welcome(){
        return "Welcome to user order area";
    }

     @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderEntityRequest orderEntityRequest){
        try {
            OrderEntityResponse response = orderService.createOrder(orderEntityRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (CustomerNotFound | ProductNotFound e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/Clear")
    public  ResponseEntity<?> OrderCanceled(@RequestBody int id ){
        log.info("Input request received"+id);
        try {
            String response = orderService.OrderCanceled(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (CustomerNotFound | ProductNotFound e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }


}
