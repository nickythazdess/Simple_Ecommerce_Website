package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter @Setter
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", unique = true)
    private String name;

    @Column(name="developer")
    private String dev;

    @Column(name="price")
    private float price;

    @ManyToOne
    @JoinColumn(name="category", nullable=false)
    private Category category;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image")
    private Image img;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "description")
    private String description;

    public Product() {}

    public Product(String name, String dev, float price, Image img, String description, LocalDate createdDate, LocalDate updatedDate) {
        this.name = name;
        this.dev = dev;
        this.price = price;
        this.img = img;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
