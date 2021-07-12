package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Category")
public class Category {
    @Id @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    @Getter @Setter
    private String name;

    @OneToMany(mappedBy="category_id")
    private Set<Product> products;
}
