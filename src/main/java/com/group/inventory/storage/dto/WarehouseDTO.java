package com.group.inventory.storage.dto;

import com.group.inventory.storage.model.StorageStatus;
import com.group.inventory.storage.validation.annotation.WarehouseValid;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@WarehouseValid
public class WarehouseDTO {
    private UUID id;

    @NotBlank(message = "{warehouse.name.blank}")
    private String name;

    @Min(value = 0, message = "{capacity.min}")
    private int capacity;

    private int currentLocationQuantity;

    @NotNull(message = "warehouse.status.null")
    private StorageStatus status;

    @NotBlank(message = "{warehouse.description.blank}")
    private String description;

    @NotBlank(message = "{warehouse.address.blank}")
    private String address;
}
