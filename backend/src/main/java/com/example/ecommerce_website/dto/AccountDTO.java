package com.example.ecommerce_website.dto;

import com.example.ecommerce_website.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class AccountDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
    public AccountDTO() {}

    public AccountDTO(Long id, String username, String name, String email, String password, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
