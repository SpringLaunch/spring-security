package com.example.userService.controller;

import com.example.userService.entity.UserEntity;
import com.example.userService.enums.Role;
import com.example.userService.jwt.JwtUtils;
import com.example.userService.model.AuthRequest;
import com.example.userService.model.AuthResponse;
import com.example.userService.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class UserAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> userLogin(@Valid @RequestBody AuthRequest authRequest){
        UserEntity user = userRepository.findEnabledUserByUsernameOrEmail(authRequest.getIdentifier())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + authRequest.getIdentifier()));

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), authRequest.getPassword())
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

        AuthResponse authResponse= new AuthResponse(token, user.getUsername(), roles);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
