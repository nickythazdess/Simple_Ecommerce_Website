package com.example.ecommerce_website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account {
    public enum account_role {
        ADMIN,
        USER;
    }

    @Id @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    @Getter @Setter
    private String name;

    @Column(name="username", unique = true)
    @Getter @Setter
    private String username;

    @Column(name="password")
    @Getter @Setter
    private String password;

    @Column(name="email", unique = true, nullable = true)
    @Getter @Setter
    private String email;

    @Column(name="role")
    @Getter @Setter
    private account_role role;
}
