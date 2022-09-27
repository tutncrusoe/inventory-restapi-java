package com.group.inventory.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.inventory.department.dto.DepartmentDTO;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.user.model.UserStatus;
import com.group.inventory.user.validation.annotation.UniqueUsername;
import com.group.inventory.user.validation.annotation.UniqueEmail;
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

    @JsonIgnore
    private String password;

    @UniqueEmail
    private String email;

    private String avatar;

    private UserStatus status;

    private DepartmentDTO departmentDTO;

    private List<RoleDTO> roles;
}
