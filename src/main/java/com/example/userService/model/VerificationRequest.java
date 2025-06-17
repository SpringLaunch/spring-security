package com.example.userService.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerificationRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(max = 6)
    private String code;
}
