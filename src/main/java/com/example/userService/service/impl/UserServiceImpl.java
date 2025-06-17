package com.example.userService.service.impl;

import com.example.userService.Utils.OtpUtils;
import com.example.userService.entity.UserEntity;
import com.example.userService.jwt.JwtUtils;
import com.example.userService.model.UserRequest;
import com.example.userService.model.VerificationRequest;
import com.example.userService.repository.UserRepository;
import com.example.userService.service.MailService;
import com.example.userService.service.RedisService;
import com.example.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private OtpUtils otpUtils;
    @Autowired
    private RedisService redisService;

    @Override
    public void addUser(UserRequest userRequest) {
        if (null != redisService.getOtp(userRequest.getEmail())){
            throw new RuntimeException("Process already ongoing for user: " + userRequest.getUsername());
        }

        Optional<UserEntity> userEntity = userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail());

        if (userEntity.isEmpty()){
            UserEntity user = new UserEntity();
            user.setUsername(userRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setEmail(userRequest.getEmail());
            user.setRoles(userRequest.getRoles());

            String code = otpUtils.generateOtp();
            redisService.saveOtp(userRequest.getEmail(), code);
            mailService.sendMail(userRequest.getEmail(), code);
            userRepository.save(user);
        } else if (userEntity.get().isEnabled()){
            throw new RuntimeException("User Already exist with email: " + userRequest.getEmail());
        }else {
            throw new RuntimeException("User registration already completed for email: " + userRequest.getEmail() +
                    ". Please proceed with email validation.");
        }
    }

    @Override
    public void resendCode(String email) {
        if (null != redisService.getOtp(email)){
            throw new RuntimeException("Otp is already generated for email: " + email);
        }

        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isEmpty()){
            throw new RuntimeException("User registration not completed for email: " + email);
        }else if (user.get().isEnabled()){
            throw new RuntimeException("User Already exist with email: " + email);
        } else {
            String code = otpUtils.generateOtp();
            redisService.saveOtp(email, code);
            mailService.sendMail(email, code);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = getUserDetails(username);

        Set<GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name())) // ROLE_USER etc.
                .collect(Collectors.toSet());

        return User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(authorities)
                .build();
    }

    @Override
    public void verify(VerificationRequest verificationRequest) {
        String code = redisService.getOtp(verificationRequest.getEmail());

        if (null == code){
            throw new RuntimeException("Otp is expired");
        }else if (!code.equals(verificationRequest.getCode())){
            throw new RuntimeException("Otp is invalid");
        }

        Optional<UserEntity> user = userRepository.findByEmail(verificationRequest.getEmail());

        if (user.isEmpty()){
            throw new RuntimeException("User registration not completed for email: " + verificationRequest.getEmail());
        }else if (user.get().isEnabled()){
            throw new RuntimeException("User Already exist with email: " + verificationRequest.getEmail());
        }else {
            UserEntity userEntity = user.get();  // unwrap Optional
            userEntity.setEnabled(true);
            userRepository.save(userEntity);
            redisService.deleteOtp(verificationRequest.getEmail());
        }
    }

    @Override
    public UserEntity getUserDetails(String username){
         return userRepository.findByUsernameAndEnabled(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
    }
}
