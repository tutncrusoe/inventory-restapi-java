package com.group.inventory.department.dto;


import com.group.inventory.department.validation.annotation.UniqueDepartmentName;
import com.group.inventory.user.dto.UserWithRolesDTOResponse;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentWithUserDTO {
    private UUID id;

    @UniqueDepartmentName
    @NotBlank(message = "{department.name.not_blank}")
    private String name;

    @NotBlank(message = "{department.description.not_blank}")
    private String description;

    private Set<UserWithRolesDTOResponse> users = new LinkedHashSet<>();
}
