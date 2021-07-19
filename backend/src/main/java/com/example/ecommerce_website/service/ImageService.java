package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    void saveImage(MultipartFile file) throws IOException;

    Image getImageById(String id);

    List<Image> getAllImages();
}
