package com.example.userService.Utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpUtils {

    public String generateOtp(){
        SecureRandom random = new SecureRandom();
        return String.valueOf(random.ints(6, 100000, 1000000).findFirst().getAsInt());
    }
}
