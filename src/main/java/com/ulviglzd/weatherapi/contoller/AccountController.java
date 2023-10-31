package com.ulviglzd.weatherapi.contoller;

import com.ulviglzd.weatherapi.entity.user.User;
import com.ulviglzd.weatherapi.repository.UserRepository;
import com.ulviglzd.weatherapi.service.AccountService;
import com.ulviglzd.weatherapi.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private final AccountService accountService;
    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("avatar")MultipartFile file) {
        //Get the username of logged-in user
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        String filePath = fileStorageService.uploadFile(file);
        accountService.uploadAvatar(filePath, username);

        return ResponseEntity.ok().build();
    }
}
