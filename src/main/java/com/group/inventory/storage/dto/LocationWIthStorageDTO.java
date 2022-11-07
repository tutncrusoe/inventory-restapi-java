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
public class LocationWIthStorageDTO {
    private UUID id;
    private String name;
    private int capacity;
    private int currentStorageQuantity;
    private StorageStatus status;
    private String description;
    private Set<StorageDTO> storages = new LinkedHashSet<>();
}
