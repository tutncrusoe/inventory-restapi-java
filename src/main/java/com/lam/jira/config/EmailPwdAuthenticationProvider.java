//package com.lam.jira.config;
//
//import com.lam.jira.models.User;
//import com.lam.jira.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.security.auth.login.CredentialNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class EmailPwdAuthenticationProvider implements AuthenticationProvider {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String email = authentication.getName();
//        String pwd = authentication.getCredentials().toString();
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
//        if (passwordEncoder.matches(pwd, user.getPassword())) {
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
//            return new UsernamePasswordAuthenticationToken(email, pwd, authorities);
//        } else {
//            throw new BadCredentialsException("Invalid password");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
