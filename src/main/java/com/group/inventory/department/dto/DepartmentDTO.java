package com.group.inventory.department.dto;

import com.group.inventory.department.validation.annotation.UniqueDepartmentName;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private UUID id;

    @Size(min = 5, max = 100, message = "Name must have length between {min} - {max} characters")
    @UniqueDepartmentName
    private String name;

    @NotBlank(message = "Description must not be blank")
    private String description;
}
