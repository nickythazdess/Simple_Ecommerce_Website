package com.example.ecommerce_website.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageDTO {
    private String id;
    private String data;
    private String name;
    private String contentType;
    private Long size;
    public ImageDTO() {}

    public ImageDTO(String id, String data, String name, String contentType, Long size) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.data = data;
        this.size = size;
    }
}