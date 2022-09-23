package com.lam.jira;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WelcomeResource {
    @GetMapping
    public String welcome(HttpServletRequest request) {
        String welcomeStr = "Welcome to %s to the Jira Application";
        return String.format(welcomeStr, request.getRemoteAddr());
    }
}
