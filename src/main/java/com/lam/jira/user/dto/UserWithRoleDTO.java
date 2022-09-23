package com.lam.jira.user.dto;

import com.lam.jira.role.dto.RoleDTO;
import com.lam.jira.user.model.UserStatus;
import com.lam.jira.user.validation.annotation.UniqueEmail;
import com.lam.jira.user.validation.annotation.UniqueUsername;

import java.util.List;
import java.util.UUID;

public class UserWithRoleDTO {
    private UUID id;

    @UniqueUsername
    private long username;

    private String displayName;

    private String password;

    @UniqueEmail
    private String email;

    private String avatar;

    private UserStatus status;

    private List<RoleDTO> roles;
}
