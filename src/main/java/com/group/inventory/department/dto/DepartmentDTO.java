package com.group.inventory.department.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentDTO {
    private UUID id;

    @NotBlank(message = "{department.name.not_blank}")
    private String name;

    @NotBlank(message = "{department.description.not_blank}")
    private String description;
}
