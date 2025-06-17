package com.example.userService.service;

import com.example.userService.entity.UserEntity;
import com.example.userService.model.UserRequest;
import com.example.userService.model.VerificationRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    void addUser(UserRequest userRequest);

    void resendCode(String email);

    UserEntity getUserDetails(String username);

    UserDetails loadUserByUsername(String username);

    void verify(VerificationRequest verificationRequest);
}
