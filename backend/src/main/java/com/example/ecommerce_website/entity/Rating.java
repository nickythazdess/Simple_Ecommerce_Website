package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter @Setter
@Table(name = "Rating")
public class Rating {
    @EmbeddedId
    private RatingID id;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "cmt", nullable = false)
    private String cmt;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public Rating() {}

    public Rating(Long uid, Long pid, int rate, String cmt, LocalDate date) {
        RatingID id = new RatingID(uid, pid);
        this.id = id;
        this.rate = rate;
        this.cmt = cmt;
        this.date = date;
    }
}
