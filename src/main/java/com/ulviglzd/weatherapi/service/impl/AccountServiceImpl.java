package com.ulviglzd.weatherapi.service.impl;

import com.ulviglzd.weatherapi.entity.user.User;
import com.ulviglzd.weatherapi.repository.UserRepository;
import com.ulviglzd.weatherapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public void uploadAvatar(String filePath, String userName) {
        userRepository.findByUserName(userName).ifPresent(user -> {
            user.setUserImg(filePath);
            userRepository.save(user);
        });
    }

}

