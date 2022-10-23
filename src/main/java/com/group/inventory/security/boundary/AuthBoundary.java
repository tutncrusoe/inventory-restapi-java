package com.group.inventory.security.boundary;

import com.group.inventory.security.dto.LoginDTO;
import com.group.inventory.security.service.AuthService;
import com.group.inventory.common.util.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthBoundary {

    private final AuthService authService;

    public AuthBoundary(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseHelper.getResponse(authService.login(loginDTO), HttpStatus.OK);
    }
}
