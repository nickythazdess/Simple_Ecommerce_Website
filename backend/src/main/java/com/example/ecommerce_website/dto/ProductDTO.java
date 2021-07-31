package com.example.ecommerce_website.dto;

import com.example.ecommerce_website.entity.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ProductDTO {
    private Long id;
    private String name;
    private String dev;
    private float price;
    private String category_name;
    private Image img_id;
    private String description;
    private float avg_rate;
    private long total;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    public ProductDTO() {}

    public ProductDTO(Long id, String name, String dev, double avg_rate, float price, String category_name, Image img_id, String description, LocalDate createdDate, LocalDate updatedDate) {
        this.id = id;
        this.name = name;
        this.dev = dev;
        this.price = price;
        this.category_name = category_name;
        this.img_id = img_id;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
