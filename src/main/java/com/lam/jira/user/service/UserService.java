package com.lam.jira.user.service;

import com.lam.jira.role.dto.RoleDTO;
import com.lam.jira.role.mapper.RoleMapper;
import com.lam.jira.role.model.Role;
import com.lam.jira.role.repository.RoleRepository;
import com.lam.jira.user.dto.RequestUserDTO;
import com.lam.jira.user.dto.UserDTO;
import com.lam.jira.user.dto.UserWithRoleDTO;
import com.lam.jira.user.mapper.UserMapper;
import com.lam.jira.user.model.User;
import com.lam.jira.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface UserService {
    UserDTO createNewUser(RequestUserDTO dto);

    List<UserDTO> findAllUser();

    List<UserWithRoleDTO> findAllUserWithRoles();

    UserDTO findUserById(String id);

    User findByEmail(String email);

    List<RoleDTO> getRolesFromUser(UUID id);

    UserWithRoleDTO findUserWithRolesById(String id);

    UserWithRoleDTO addRole(String userId, String roleId);

    UserWithRoleDTO removeRole(String userId, String roleId);

    int deleteByEmail(String email);
}

@Service
@Transactional
class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserWithRoleDTO> findAllUserWithRoles() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::toUserWithRoleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findUserById(String id) {
        Optional<User> userOpt = userRepository.findById(UUID.fromString(id));
        if (userOpt.isEmpty()) {
            return null;
        }
        return UserMapper.INSTANCE.toUserDTO(userOpt.get());
    }

    @Override
    @Transactional(readOnly = true)
    public UserWithRoleDTO findUserWithRolesById(String id) {
        Optional<User> userOpt = userRepository.findUserWithRolesById(UUID.fromString(id));
        if (userOpt.isEmpty()) {
            return null;
        }
        return UserMapper.INSTANCE.toUserWithRoleDTO(userOpt.get());
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return null;
        }
        return userOpt.get();
    }

    @Override
    public UserDTO createNewUser(RequestUserDTO dto) {
        User user = UserMapper.INSTANCE.mapToEntity(dto);

        user.setPassword(encoder.encode(dto.getPassword()));

        User newUser = userRepository.save(user);

        newUser.setPassword("");

        dto.getRoleIdList().forEach(roleId -> {
            addRole(newUser.getId().toString(), roleId);
        });

        return UserMapper.INSTANCE.toUserDTO(newUser);
    }

    @Override
    public UserWithRoleDTO addRole(String userId, String roleId) {
        Optional<User> userOpt;
        Optional<Role> roleOpt;

        try {
            userOpt = userRepository.findById(UUID.fromString(userId));
            roleOpt = roleRepository.findById(UUID.fromString(roleId));
        } catch (EntityNotFoundException e) {
            return null;
        }

        if (userOpt.isPresent() && roleOpt.isPresent()) {
            User user = userOpt.get();
            Role role = roleOpt.get();
            user.addRole(role);

            User modifiedUser = userRepository.save(user);
            return UserMapper.INSTANCE.toUserWithRoleDTO(modifiedUser);
        }

        return null;
    }

    @Override
    public UserWithRoleDTO removeRole(String userId, String roleId) {
        Optional<User> userOpt;
        Optional<Role> roleOpt;

        try {
            userOpt = userRepository.findById(UUID.fromString(userId));
            roleOpt = roleRepository.findById(UUID.fromString(roleId));
        } catch (EntityNotFoundException e) {
            return null;
        }

        if (userOpt.isPresent() && roleOpt.isPresent()) {
            User user = userOpt.get();
            Role role = roleOpt.get();
            user.removeRole(role);

            User modifiedUser = userRepository.save(user);
            return UserMapper.INSTANCE.toUserWithRoleDTO(modifiedUser);
        }

        return null;
    }

    @Override
    public List<RoleDTO> getRolesFromUser(UUID uuid) {
        Optional<User> userOpt = userRepository.findUserWithRolesById(uuid);

        if (userOpt.isEmpty()) {
            return null;
        }

        return userOpt.get().getRoles()
                .stream()
                .map(RoleMapper.INSTANCE::toRoleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int deleteByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }
}