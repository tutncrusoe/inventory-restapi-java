package com.group.inventory.storage.dto;

import com.group.inventory.storage.validation.annotation.UniqueWarehouseName;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CreatedWarehouseDTO {

    @NotBlank(message = "{warehouse.name.blank}")
    @UniqueWarehouseName
    private String name;

    @NotBlank(message = "{warehouse.description.blank}")
    private String description;

    @NotBlank(message = "{warehouse.address.blank}")
    private String address;

    @Min(value = 0, message = "{capacity.min}")
    private int capacity;
}
