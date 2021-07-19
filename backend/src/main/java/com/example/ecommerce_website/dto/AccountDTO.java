package com.example.ecommerce_website.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    public AccountDTO() {}

    public AccountDTO(Long id, String username, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
