package com.group.inventory.storage.service;

import com.group.inventory.common.dto.PagingRequestDTO;
import com.group.inventory.common.dto.PagingResponseDTO;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.storage.dto.CreatedLocationDTO;
import com.group.inventory.storage.dto.LocationDTO;
import com.group.inventory.storage.dto.LocationWIthStorageDTO;
import com.group.inventory.storage.model.Location;

import java.util.List;
import java.util.UUID;

public interface LocationService extends GenericService<Location, LocationDTO, UUID> {
    LocationWIthStorageDTO addStorages(UUID id, List<UUID> storageIds);

    LocationWIthStorageDTO removeStorages(UUID id, List<UUID> storageIds);

    PagingResponseDTO findAllWithStorage(PagingRequestDTO request);

    LocationWIthStorageDTO findLocationWithStoragesById(UUID id);

    LocationDTO update(UUID id, LocationDTO dto);

    LocationDTO save(CreatedLocationDTO dto);
}
