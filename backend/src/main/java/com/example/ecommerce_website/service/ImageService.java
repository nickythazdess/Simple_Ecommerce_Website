package com.example.ecommerce_website.service;

import com.example.ecommerce_website.dto.ImageDTO;
import com.example.ecommerce_website.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> getImageList();

    Optional<Image> getImageById(String id);

    Optional<Image> getImageOfProduct(Long pid);

    void saveImage(Image image) throws IOException;

    void deleteImage(String id);

    ImageDTO convertToDto(Image img);

    List<ImageDTO> convertToDtoList(List<Image> imgList);
}
