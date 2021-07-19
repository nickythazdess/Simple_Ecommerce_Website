package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name="img")
    private String img;

    //@Column(name = "createdIn")
    //private String created_in;

    //@Column(name = "updatedIn")
    //private String updated_in;

    public Product() {}

    public Product(Long id, String name, String dev, float price, String img) {
        this.id = id;
        this.name = name;
        this.dev = dev;
        this.price = price;
        this.img = img;
    }
}
