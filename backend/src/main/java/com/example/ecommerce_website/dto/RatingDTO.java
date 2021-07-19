package com.example.ecommerce_website.dto;

import com.example.ecommerce_website.entity.RatingID;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import java.time.LocalDate;

@Getter @Setter
public class RatingDTO {
    private Long uid;

    private Long pid;

    private int rate;

    private String cmt;

    private LocalDate date;

    public RatingDTO() {}

    public RatingDTO(Long uid, Long pid, int rate, String cmt, LocalDate date) {
        this.uid = uid;
        this.pid = pid;
        this.rate = rate;
        this.cmt = cmt;
        this.date = date;
    }
}
