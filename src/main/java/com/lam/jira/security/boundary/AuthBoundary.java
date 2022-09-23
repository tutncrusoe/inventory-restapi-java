package com.lam.jira.security.boundary;

import com.lam.jira.common.util.ResponseHelper;
import com.lam.jira.security.dto.LoginDTO;
import com.lam.jira.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthBoundary {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHelper.getErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);
        }

        String token = authService.login(loginDTO);

        if (token == null) {
            return ResponseHelper.getErrorResponse("Password is not correct", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseHelper.getResponse(token, HttpStatus.OK);
        }
    }
}
