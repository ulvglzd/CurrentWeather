package com.ulviglzd.weatherapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String uploadUserProfileImage(MultipartFile file, String username);
}
