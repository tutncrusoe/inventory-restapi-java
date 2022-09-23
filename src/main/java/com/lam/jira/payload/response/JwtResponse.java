package com.lam.jira.payload.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.net.URI;
import java.net.URL;
import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String jwt;
    private long id;
    private String username;
    private String email;

    private String avatarUrl;
    private List<String> roles;

    public JwtResponse(String jwt, Long id, String username, String email, String avatarUrl, List<String> roles) {
        this.jwt = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.roles = roles;
    }
}
