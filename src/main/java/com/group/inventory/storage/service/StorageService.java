package com.group.inventory.storage.service;

import com.group.inventory.common.dto.PagingRequestDTO;
import com.group.inventory.common.dto.PagingResponseDTO;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.storage.dto.CreatedStorageDTO;
import com.group.inventory.storage.dto.StorageDTO;
import com.group.inventory.storage.dto.StorageWithPartsDTO;
import com.group.inventory.storage.model.Storage;
import com.group.inventory.storage.model.StorageStatus;

import java.util.List;
import java.util.UUID;

public interface StorageService extends GenericService<Storage, StorageDTO, UUID> {
    PagingResponseDTO findAllWithParts(PagingRequestDTO request);

    StorageWithPartsDTO findStorageWithPartsById(UUID id);

    StorageWithPartsDTO addParts(UUID id, List<UUID> partIds);

    StorageWithPartsDTO removeParts(UUID id, List<UUID> partIds);

    StorageDTO changeStatus(UUID id, StorageStatus status);

    StorageDTO create(CreatedStorageDTO storage);

    StorageDTO update(UUID id, StorageDTO storage);
}
