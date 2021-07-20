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

    //@Lob
    //@Type(type="org.hibernate.type.MaterializedBlobType")
    @Column(name = "data", length = Integer.MAX_VALUE)
    private String data;

    @Column(name = "name")
    private String name;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;

    public Image() {}

    public Image(String data, String name, String contentType, Long size) {
        this.data = data;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
    }
}
