package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity @Getter @Setter
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name", unique = true)
    private String name;

    @OneToMany(mappedBy="category")
    private Set<Product> products;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }
}
