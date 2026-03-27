package com.example.BigBazzarServer.annotations;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AOPprint {

    @Before("@annotation(com.example.BigBazzarServer.annotations.Print_Hello)")
    public void print() {
        System.out.println("Hello World");
    }
}
