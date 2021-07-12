package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Product")
public class Product {

    @Id @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    @Getter @Setter
    private String name;

    @Column(name="developer")
    @Getter @Setter
    private String dev;

    @Column(name="price")
    @Getter @Setter
    private float price;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name="category_id", nullable=false)
    private Category category_id;

    /*@Column(name="category_id")
    @Getter @Setter
    private long category_ID;*/

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rid", referencedColumnName = "id")
    private List<Rating> ratingList = new ArrayList<>();

    @Column(name="img")
    @Getter @Setter
    private String img_path;


}
