package com.group.inventory.security.service;

import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.security.dto.UserDetailsImpl;
import com.group.inventory.user.model.User;
import com.group.inventory.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);

        if (user == null) {
            return null;
        }

        return new UserDetailsImpl(user.getEmail(), user.getPassword(), getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        List<RoleDTO> roleDTOList = userService.getRolesFromUser(user.getId());

        roleDTOList.forEach(
                roleDTO -> authorities.add(
                        new SimpleGrantedAuthority(roleDTO.getCode())
                ));

        return authorities;
    }
}
