package com.example.ecommerce_website.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "Rating")
public class Rating {
    @Id @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private long uID;
    private long pID;
    private int rate;
}
