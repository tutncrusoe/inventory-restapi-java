package com.group.inventory.role.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RoleDTO {
    private UUID id;

    @NotNull
    private String name;

    @Size(min = 5, max = 100, message = "Role Code must be 5 characters.")
    private String code;

    @NotBlank(message = "Description must be not blank.")
    private String description;
}
