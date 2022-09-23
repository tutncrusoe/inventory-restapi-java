package com.lam.jira.security.service;

import com.lam.jira.security.dto.LoginDTO;
import com.lam.jira.security.jwt.JwtUtils;
import com.lam.jira.user.model.User;
import com.lam.jira.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface AuthService {
    String login(LoginDTO dto);
}

@Service
class AuthServiceImpl implements  AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public String login(LoginDTO dto) {
        User user = userService.findByEmail(dto.getEmail());

        if (user == null) {
            return null;
        }

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return jwtUtils.generateJwtToken(dto.getEmail());
        }

        return null;
    }
}
