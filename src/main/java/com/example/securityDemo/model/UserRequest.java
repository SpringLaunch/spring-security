package com.example.securityDemo.model;

import com.example.securityDemo.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private String username;
    private String password;
    private List<Role> roles;
}
