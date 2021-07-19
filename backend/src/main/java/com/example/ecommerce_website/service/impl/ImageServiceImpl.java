package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Image;
import com.example.ecommerce_website.repository.ImageRepository;
import com.example.ecommerce_website.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public Image getImageById(String image_id) {
        return imageRepository.findById(image_id).get();
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public void saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setContentType(file.getContentType());
        image.setData(file.getBytes());
        image.setSize(file.getSize());

        imageRepository.save(image);
    }
}
