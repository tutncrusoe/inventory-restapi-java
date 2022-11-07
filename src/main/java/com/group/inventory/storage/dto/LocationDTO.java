package com.group.inventory.storage.dto;

import com.group.inventory.storage.model.StorageStatus;
import com.group.inventory.storage.validation.annotation.LocationValid;
import com.group.inventory.storage.validation.annotation.UniqueLocationName;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@LocationValid
public class LocationDTO {
    private UUID id;

    @NotBlank(message = "{location.name.blank}")
    @UniqueLocationName
    private String name;

    @Min(value = 0, message = "{capacity.min}")
    private int capacity;

    private int currentStorageQuantity;

    @NotNull(message = "{location.status.null}")
    private StorageStatus status;

    @NotBlank(message = "{location.description.blank}")
    private String description;
}
