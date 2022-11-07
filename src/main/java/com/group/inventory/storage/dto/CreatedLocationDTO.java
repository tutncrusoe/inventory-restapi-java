package com.group.inventory.storage.dto;

import com.group.inventory.storage.validation.annotation.UniqueLocationName;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreatedLocationDTO {
    @UniqueLocationName(message = "{storage.name.existed}")
    @NotBlank(message = "{location.name.blank}")
    private String name;

    @Min(value = 0, message = "{capacity.min}")
    private int capacity;

    @NotBlank(message = "{location.description.blank}")
    private String description;
}
