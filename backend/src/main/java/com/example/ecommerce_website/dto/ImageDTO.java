package com.example.ecommerce_website.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageDTO {
    private String id;
    private String name;
    private String data;
    public ImageDTO() {}

    public ImageDTO(String id, String data, String name) {
        this.id = id;
        this.name = name;
        this.data = data;
    }
}