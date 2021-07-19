package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity @Getter @Setter
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "data", length = Integer.MAX_VALUE)
    private byte[] data;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;
}
