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
}
