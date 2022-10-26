package com.group.inventory.user.dto;

import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.user.model.UserStatus;
import lombok.*;

import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link } entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithRolesDTOResponse {

    private UUID id;

    private String username;

    private String email;

    private String avatar;

    private UserStatus status;

    private Set<RoleDTO> roles;
}
