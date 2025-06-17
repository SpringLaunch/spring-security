package com.example.userService.service.impl;

import com.example.userService.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String mail, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail);
        mailMessage.setSubject("Here is your OTP to login to Spring Launch");
        mailMessage.setText(code + " is your OTP to login to Spring Launch. " +
                "OTP will be valid for 5 minutes. Please do not share it with anyone.");

        javaMailSender.send(mailMessage);
    }
}
