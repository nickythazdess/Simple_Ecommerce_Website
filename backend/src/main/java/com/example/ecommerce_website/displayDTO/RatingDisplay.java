package com.example.ecommerce_website.displayDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RatingDisplay {
    private String username;
    private int rate;
    private String cmt;
    private LocalDate date;
}
