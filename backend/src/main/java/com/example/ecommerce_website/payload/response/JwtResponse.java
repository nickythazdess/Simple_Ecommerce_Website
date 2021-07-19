package com.example.ecommerce_website.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class JwtResponse {
    @Setter
    private String token;

    @Setter
    private String type = "Bearer";

    @Setter
    private Long id;

    @Setter
    private String username;

    @Setter
    private String email;

    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
