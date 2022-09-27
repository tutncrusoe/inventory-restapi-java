package com.group.inventory.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtils.getToken(request);

        if (jwtUtils.validate(token)) {
            // get credential and set security context
            String username = jwtUtils.getUsername(token);
            UserDetails userDetails = service.loadUserByUsername(username);
            Authentication auth
                    = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);

            // authorization

        }
        filterChain.doFilter(request, response);
    }
}
