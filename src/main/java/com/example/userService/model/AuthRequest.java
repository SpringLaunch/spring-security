package com.example.userService.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequest {

    @NotEmpty
    private String identifier;

    @NotEmpty
    private String password;
}
