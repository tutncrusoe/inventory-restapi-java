package com.group.inventory.storage.dto;

import com.group.inventory.parts.dto.PartDetailsDTO;
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
public class StorageWithPartsDTO {
    private UUID id;
    private String name;
    private String code;
    private String description;
    private float volume;
    private float currentVolume;
    private StorageStatus status;
    private Set<PartDetailsDTO> partDetails = new LinkedHashSet<>();
}
