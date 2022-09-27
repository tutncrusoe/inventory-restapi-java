package com.group.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfiguration {
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    static class AuditorAwareImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null) {
                return Optional.ofNullable("");
            }

            if (auth.getPrincipal() instanceof Principal) {
                String authPrincipal = (String) auth.getPrincipal();
                return Optional.ofNullable(authPrincipal);
            }

            UserDetails currentAuditor = (UserDetails) auth.getPrincipal();
            return Optional.ofNullable(currentAuditor.getUsername());
        }
    }
}
