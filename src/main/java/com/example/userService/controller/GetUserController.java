package com.example.userService.controller;

import com.example.userService.entity.UserEntity;
import com.example.userService.model.UserResponse;
import com.example.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    public ResponseEntity<UserResponse> userDetails(@PathVariable String username){
        UserEntity userEntity = userService.getUserDetails(username);

        UserResponse userResponse = new UserResponse(userEntity.getUsername(), userEntity.getEmail(), userEntity.getRoles());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
