package com.example.BigBazzarServer.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Home")
@Slf4j
@RequiredArgsConstructor
public class Home {

    @GetMapping()
    public String Home(){
        return "Hello Homes!!";
    }

}
