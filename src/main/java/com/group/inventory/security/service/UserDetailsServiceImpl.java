package com.group.inventory.security.service;

import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.security.dto.UserDetailsImpl;
import com.group.inventory.user.model.User;
import com.group.inventory.user.repository.UserRepository;
import com.group.inventory.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserService userService;

    @Value("${spring.security.user.name}")
    private String adminUserName;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Value("${spring.security.user.role}")
    private String adminRole;

    public UserDetailsServiceImpl(@Lazy UserRepository userRepository,@Lazy UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (Objects.equals(adminUserName, email)) {
            return org.springframework.security.core.userdetails.User
                    .builder().username(adminUserName).password(adminPassword).roles(adminRole).build();
        }

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new UserDetailsImpl(user.getEmail(), user.getPassword(), getGrantedAuthorities(user));
        }

        throw new InventoryBusinessException("Email is not existed.");
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        Set<RoleDTO> roleDTOList = userService.getRolesFromUser(user.getId());

        roleDTOList.forEach(
                roleDTO -> authorities.add(
                        new SimpleGrantedAuthority(roleDTO.getName().toString())
                ));

        return authorities;
    }
}
