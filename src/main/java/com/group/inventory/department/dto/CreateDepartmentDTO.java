package com.group.inventory.department.dto;

import com.group.inventory.department.validation.annotation.UniqueDepartmentName;
import lombok.*;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateDepartmentDTO {
    @UniqueDepartmentName
    @NotBlank(message = "{department.name.not_blank}")
    private String name;

    @NotBlank(message = "{department.description.not_blank}")
    private String description;
}
