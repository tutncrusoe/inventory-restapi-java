package com.group.inventory.storage.service;

import com.group.inventory.common.dto.PagingRequestDTO;
import com.group.inventory.common.dto.PagingResponseDTO;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.storage.dto.CreatedWarehouseDTO;
import com.group.inventory.storage.dto.WarehouseDTO;
import com.group.inventory.storage.dto.WarehouseWithLocationsDTO;
import com.group.inventory.storage.model.Warehouse;

import java.util.List;
import java.util.UUID;

public interface WarehouseService extends GenericService<Warehouse, WarehouseDTO, UUID> {

    WarehouseWithLocationsDTO addLocations(UUID id, List<UUID> locationIds);

    WarehouseWithLocationsDTO removeLocations(UUID id, List<UUID> locationIds);

    PagingResponseDTO findAllWithLocations(PagingRequestDTO request);

    WarehouseWithLocationsDTO findWithLocationsById(UUID id);

    WarehouseDTO save(CreatedWarehouseDTO dto);

    WarehouseDTO update(UUID id, WarehouseDTO dto);
}
