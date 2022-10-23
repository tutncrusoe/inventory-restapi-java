package com.group.inventory.security.service;

import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.security.jwt.JwtUtils;
import com.group.inventory.security.dto.LoginDTO;
import com.group.inventory.user.model.User;
import com.group.inventory.user.model.UserStatus;
import com.group.inventory.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

public interface AuthService {
    String login(LoginDTO dto);
}

@Service
class AuthServiceImpl implements  AuthService {

    @Lazy
    @Autowired
    private UserRepository userRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    private JwtUtils jwtUtils;

    @Value("${spring.security.user.name}")
    private String adminUserName;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Override
    public String login(LoginDTO dto) {
        if (Objects.equals(dto.getEmail(), adminUserName) && Objects.equals(dto.getPassword(), adminPassword)) {
            return jwtUtils.generateJwtToken(adminUserName);
        }

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InventoryBusinessException("Email is not existed."));

        if (Objects.equals(user.getStatus(), UserStatus.BLOCKED)) {
            throw new InventoryBusinessException("User is blocked.");
        }

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return jwtUtils.generateJwtToken(dto.getEmail());
        }

        throw new InventoryBusinessException("Password is not correct.");
    }
}
