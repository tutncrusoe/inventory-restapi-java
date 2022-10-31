package com.group.inventory.security.jwt;

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

    private final JwtUtils jwtUtils;

    private final UserDetailsService service;

    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsService service) {
        this.jwtUtils = jwtUtils;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtils.getToken(request);

        String path = request.getServletPath();

        if (!(path.startsWith("/api/v1/auth/login")
                || path.startsWith("/api/v1/users/sign-up")
                || path.startsWith("/swagger-ui/index.html/**")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/v3/api-docs/swagger-config"))) {
            if (jwtUtils.validateJwt(token)) {
                // get credential and set security context
                String email = jwtUtils.getEmail(token);

                UserDetails userDetails = service.loadUserByUsername(email);

                Authentication auth
                        = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
