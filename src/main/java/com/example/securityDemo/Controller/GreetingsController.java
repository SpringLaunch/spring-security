package com.example.securityDemo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring")
public class GreetingsController {

    @GetMapping("/hello")
    public String sendGreetings(){
        return "Hello User! Welcome to Spring Launch";
    }
}
