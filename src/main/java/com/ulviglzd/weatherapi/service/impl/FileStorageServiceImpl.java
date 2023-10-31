package com.ulviglzd.weatherapi.service.impl;

import com.ulviglzd.weatherapi.exceptions.UnableToUploadFileException;
import com.ulviglzd.weatherapi.service.FileStorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${image.storage.path}")
    private String IMAGE_STORAGE_PATH;

    @Override
    public String uploadFile(MultipartFile file) {
        String filePath = IMAGE_STORAGE_PATH + file.getOriginalFilename();
        System.out.println(filePath);
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new UnableToUploadFileException(e.getMessage());
        }

        return filePath;
    }
}
