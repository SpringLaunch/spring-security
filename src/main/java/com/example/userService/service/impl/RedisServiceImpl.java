package com.example.userService.service.impl;

import com.example.userService.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate <String, String> redisTemplate;

    private static final String USER_VERIFICATION_KEY_PREFIX = "USER_VERIFICATION_";

    @Value("${user.registration.timeout}")
    private long userRegistrationTimeout;

    @Override
    public void saveOtp(String mail, String code) {
        redisTemplate.opsForValue().set(USER_VERIFICATION_KEY_PREFIX + mail, code, 300, TimeUnit.SECONDS);
    }

    @Override
    public String getOtp(String mail) {
        return redisTemplate.opsForValue().get(USER_VERIFICATION_KEY_PREFIX + mail);
    }

    @Override
    public void deleteOtp(String mail) {
        redisTemplate.delete(USER_VERIFICATION_KEY_PREFIX + mail);
    }
}
