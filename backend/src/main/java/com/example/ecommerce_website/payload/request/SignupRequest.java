package com.example.ecommerce_website.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

@Getter @Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 100)
    private String username;

    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}
