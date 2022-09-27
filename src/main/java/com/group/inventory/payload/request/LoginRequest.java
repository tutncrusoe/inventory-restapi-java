package com.group.inventory.payload.request;

import lombok.NonNull;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NonNull
    @NotBlank
    private String email;
    @NonNull
    @NotBlank
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

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
