package com.ulviglzd.weatherapi.service.impl;

import com.ulviglzd.weatherapi.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSendingService {

    private final JavaMailSender javaMailSender;

    public MailSendingService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private static final Logger log = LoggerFactory.getLogger(MailSendingService.class);

    @Value("${spring.mail.username}")
    private String username;

    public void sendMail(String to, String subject, String text) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);

        log.info("Mail sent to: " + to);
    }
}
