package com.group.inventory.role.dto;

import com.group.inventory.role.model.ERole;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    @NotBlank(message = "{desc.blank}")
    private String description;
}
