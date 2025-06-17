package com.example.userService.model;

import com.example.userService.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    @NotEmpty
    @Size(min = 4, max = 16)
    private String username;

    @NotEmpty
    @Size(min = 8, max = 16)
    private String password;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private List<Role> roles;
}
