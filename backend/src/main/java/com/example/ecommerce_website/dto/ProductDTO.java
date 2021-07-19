package com.example.ecommerce_website.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDTO {
    private Long id;
    private String name;
    private String dev;
    private float price;
    private String category_name;
    private String img;
    public ProductDTO() {}

    public ProductDTO(Long id, String name, String dev, float price, String category_name, String img) {
        this.id = id;
        this.name = name;
        this.dev = dev;
        this.price = price;
        this.category_name = category_name;
        this.img = img;

    }
}
