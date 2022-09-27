package com.group.inventory.security.service;

import com.group.inventory.security.jwt.JwtUtils;
import com.group.inventory.security.dto.LoginDTO;
import com.group.inventory.user.model.User;
import com.group.inventory.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
