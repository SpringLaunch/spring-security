package com.example.userService.service;

public interface RedisService {

    void saveOtp(String mail, String code);

    String getOtp(String mail);

    void deleteOtp(String mail);
}