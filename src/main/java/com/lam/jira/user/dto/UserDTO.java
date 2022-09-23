package com.lam.jira.user.dto;

import com.lam.jira.user.model.UserStatus;
import com.lam.jira.user.validation.annotation.UniqueEmail;
import com.lam.jira.user.validation.annotation.UniqueUsername;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDTO {
    private UUID id;

    @UniqueUsername
    private long username;

    private String displayName;

    private String password;

    @UniqueEmail
    private String email;

    private String avatar;

    private UserStatus status;
}
