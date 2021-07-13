package com.example.ecommerce_website.entity;

import com.example.ecommerce_website.dto.ProductDTO;
import com.example.ecommerce_website.service.CategoryService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category", nullable=false)
    private Category category;

    /*@Column(name="category_id")
    @Getter @Setter
    private long category_ID;*/

    //@OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "rid", referencedColumnName = "id")
    //private List<Rating> ratingList = new ArrayList<>();

    @Column(name="img")
    private String img;
}
