package com.esprit.spring.ftthback.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email; // Modifier le champ username en email

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}