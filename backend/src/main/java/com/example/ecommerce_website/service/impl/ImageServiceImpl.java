package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.displayDTO.CategoryDisplay;
import com.example.ecommerce_website.displayDTO.ImageDisplay;
import com.example.ecommerce_website.dto.CategoryDTO;
import com.example.ecommerce_website.dto.ImageDTO;
import com.example.ecommerce_website.entity.Image;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.exception.product.ProductNotFoundException;
import com.example.ecommerce_website.repository.ImageRepository;
import com.example.ecommerce_website.service.ImageService;
import com.example.ecommerce_website.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    public List<Image> getImageList() {
        return imageRepository.findAll();
    }

    public Optional<Image> getImageById(String image_id) {
        return imageRepository.findById(image_id);
    }

    public Optional<Image> getImageOfProduct(Long pid) {
        Optional<Product> product = productService.getProduct(pid);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(pid);
        }
        return imageRepository.findById(product.get().getImg().getId());
    }

    public void saveImage(Image image) throws IOException {
        imageRepository.save(image);
    }

    public void deleteImage(String id) {
        imageRepository.deleteById(id);
    }

    public ImageDTO convertToDto(Image img){
        /*String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(img.getId())
                .toUriString();*/
        //ImageDTO imageDTO = modelMapper.map(img, ImageDTO.class);
        //imageDTO.setUrl(downloadURL);
        return modelMapper.map(img, ImageDTO.class);
    }

    public List<ImageDTO> convertToDtoList(List<Image> imgList){
        List<ImageDTO> dtoList = new ArrayList<>();
        for (Image img : imgList) {
            dtoList.add(convertToDto(img));
        }
        return dtoList;
    }

    public Image convertToEntity(ImageDTO imgDTO) throws ParseException {
        Image img = modelMapper.map(imgDTO, Image.class);
        return img;
    }

    public ImageDisplay convertToDisplay(ImageDTO dto){
        ImageDisplay imageDisplay = modelMapper.map(dto, ImageDisplay.class);
        return imageDisplay;
    }

    public List<ImageDisplay> convertToDisplayList(List<ImageDTO> dtoList){
        List<ImageDisplay> displayList = new ArrayList<>();
        for (ImageDTO dto : dtoList) {
            displayList.add(convertToDisplay(dto));
        }
        return displayList;
    }
}
