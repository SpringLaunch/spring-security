package com.example.userService.model;

import com.example.userService.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private String username;

    private String email;

    private List<Role> roles;
}
