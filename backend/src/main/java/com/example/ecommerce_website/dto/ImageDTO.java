package com.example.ecommerce_website.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageDTO {
    private String id;
    private String url;
    private String contentType;
    private Long size;
    public ImageDTO() {}

    public ImageDTO(String id, String url, String contentType, Long size) {
        this.id = id;
        this.contentType = contentType;
        this.url = url;
        this.size = size;
    }
}