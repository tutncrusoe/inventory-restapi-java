package com.group.inventory.storage.dto;

import com.group.inventory.storage.model.StorageStatus;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WarehouseWithLocationsDTO {
    private UUID id;
    private String name;
    private int capacity;
    private int currentLocationQuantity;
    private StorageStatus status;
    private String description;
    private String address;
    private Set<LocationDTO> locations = new LinkedHashSet<>();
}
