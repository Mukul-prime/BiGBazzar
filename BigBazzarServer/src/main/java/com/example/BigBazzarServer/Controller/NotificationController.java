package com.example.BigBazzarServer.Controller;

import com.example.BigBazzarServer.DTO.Request.NotificationcreateRequest;
import com.example.BigBazzarServer.Exception.AlreadyNotifiedByCustomer;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/Notification/")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private  final NotificationService notificationService;
// create a notification
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> CreateNotification(Authentication authentication, @ModelAttribute NotificationcreateRequest  notification) {
        log.debug("NotificationController: CreateNotification");
        try{
            String email = authentication.getName();
            String response = notificationService.notificationCretaed(notification , email);
             return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (CustomerNotFound | IOException | AlreadyNotifiedByCustomer e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }






}
