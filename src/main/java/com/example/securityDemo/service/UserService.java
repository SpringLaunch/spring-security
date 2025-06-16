package com.example.securityDemo.service;

import com.example.securityDemo.enums.Role;
import com.example.securityDemo.model.UserRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    void addUser(UserRequest userRequest);

    List<Role> getRolesFromUsername(String username);

    UserDetails loadUserByUsername(String username);
}
