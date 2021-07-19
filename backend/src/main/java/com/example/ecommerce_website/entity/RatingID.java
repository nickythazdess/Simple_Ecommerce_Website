package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable @Getter @Setter
public class RatingID implements Serializable {
    @Column(name = "uid")
    private Long uid;

    @Column(name = "pid")
    private Long pid;

    protected RatingID() {
        super();
    }

    public RatingID(Long uid, Long pid) {
        super();
        this.pid = pid;
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RatingID other = (RatingID) o;
        if (!other.pid.equals(pid)) return false;
        return Objects.equals(uid, other.uid);
    }
}
