package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @OneToOne
    @JoinColumn(name = "image")
    private Image img;

    /*@Column(name = "created_in")
    private LocalDateTime createdIn;

    @Column(name = "updated_in")
    private LocalDateTime updatedIn;

    @Column(name = "description")
    private String description;*/

    public Product() {}

    public Product(Long id, String name, String dev, float price, Image img) {
        this.id = id;
        this.name = name;
        this.dev = dev;
        this.price = price;
        this.img = img;
    }
}
