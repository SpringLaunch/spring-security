package com.example.userService.controller;

import com.example.userService.model.UserRequest;
import com.example.userService.model.VerificationRequest;
import com.example.userService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return new ResponseEntity<>("Verification code sent to your email.", HttpStatus.CREATED);
    }

    @PostMapping("/resend-code")
    public ResponseEntity<String> resendCode(@RequestBody String email){
        userService.resendCode(email);
        return new ResponseEntity<>("Verification code sent to your email.", HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@Valid @RequestBody VerificationRequest verificationRequest){
        userService.verify(verificationRequest);
        return new ResponseEntity<>("\"User verified successfully.", HttpStatus.OK);
    }
}
