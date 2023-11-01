package com.ulviglzd.weatherapi.service.impl;

import com.ulviglzd.weatherapi.exceptions.UnableToUploadFileException;
import com.ulviglzd.weatherapi.exceptions.UnsupportedFileFormatException;
import com.ulviglzd.weatherapi.helpers.formatters.CustomDateFormatter;
import com.ulviglzd.weatherapi.helpers.validators.AllowedFileTypes;
import com.ulviglzd.weatherapi.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private ResourceLoader resourceLoader;
    @Value("${image.storage.path}")
    private String folderPath;
    private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);


    @Override
    public String uploadUserProfileImage(MultipartFile imageFile, String username) {
        String filePath = null;

        //Check if filetype is allowed
        String contentType = imageFile.getContentType();
        if(!AllowedFileTypes.isImageAllowed(contentType)){
            throw new UnsupportedFileFormatException("Invalid file format. Only PNG, JPEG, and JPG images are allowed.");
        }

        try {
            //getting the absolute path of the folder
            String absoluteFilePath = resourceLoader
                    .getResource(folderPath)
                    .getFile()
                    .getAbsolutePath();

            SimpleDateFormat dateFormat = CustomDateFormatter.dateFormatToConcatToFiles;
            String timestamp = dateFormat.format(new Date());

            //creating unique filename
            filePath = absoluteFilePath + "\\" + username + timestamp + "_" + imageFile.getOriginalFilename();
            imageFile.transferTo(new File(filePath));

            log.info("File uploaded to: " + filePath + "successfully.");

        } catch (IOException e) {
            log.error("Unable to upload file: " + e.getMessage());
            throw new UnableToUploadFileException(e.getMessage());
        }

        return filePath;
    }


}
