package com.example.securityDemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring")
public class GreetingsController {

    @GetMapping("/hello")
    @PreAuthorize("isAuthenticated()")
    public String sendGreetings(){
        return "Welcome to Spring Launch";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin(){
        return "Hello Admin! Welcome to Spring Launch";
    }

    @GetMapping("/user")
    public String helloUser(){
        return "Hello User! Welcome to Spring Launch";
    }
}
