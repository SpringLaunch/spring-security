package com.example.securityDemo.controller;

import com.example.securityDemo.enums.Role;
import com.example.securityDemo.jwt.JwtUtils;
import com.example.securityDemo.model.AuthRequest;
import com.example.securityDemo.model.AuthResponse;
import com.example.securityDemo.model.UserRequest;
import com.example.securityDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/addUser")
    public ResponseEntity<Void> addUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> userLogin(@RequestBody AuthRequest authRequest){
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtUtils.generateToken(userDetails.getUsername());
        List<Role> roles = userDetails.getAuthorities().stream()
                .map(a -> Role.valueOf(a.getAuthority()))
                .toList();

        AuthResponse authResponse= new AuthResponse(token, authRequest.getUsername(), roles);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    public ResponseEntity<List<Role>> getRolesFromUsername(@PathVariable String username){
        List<Role> roles = userService.getRolesFromUsername(username);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
