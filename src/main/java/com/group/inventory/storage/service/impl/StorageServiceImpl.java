package com.group.inventory.storage.service.impl;

import com.group.inventory.common.dto.PagingRequestDTO;
import com.group.inventory.common.dto.PagingResponseDTO;
import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.parts.model.PartDetails;
import com.group.inventory.parts.service.PartDetailsService;
import com.group.inventory.storage.dto.CreatedStorageDTO;
import com.group.inventory.storage.dto.StorageDTO;
import com.group.inventory.storage.dto.StorageWithPartsDTO;
import com.group.inventory.storage.model.Storage;
import com.group.inventory.storage.model.StorageStatus;
import com.group.inventory.storage.repository.StorageRepository;
import com.group.inventory.storage.service.StorageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.group.inventory.common.constants.ExceptionMessageConstants.STORAGE_NOT_FOUND;

@Service
@Transactional
public class StorageServiceImpl implements StorageService {
    private final BaseMapper mapper;
    private final StorageRepository repository;
    private final PartDetailsService partDetailsService;

    public StorageServiceImpl(BaseMapper mapper, StorageRepository repository, PartDetailsService partDetailsService) {
        this.mapper = mapper;
        this.repository = repository;
        this.partDetailsService = partDetailsService;
    }

    @Override
    public JpaRepository<Storage, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return mapper;
    }

    @Override
    public StorageDTO update(UUID id, StorageDTO dto) {
        Storage storage = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(STORAGE_NOT_FOUND)
                );

        storage.setName(dto.getName());
        storage.setCode(dto.getCode());
        storage.setVolume(dto.getVolume());
        storage.setDescription(dto.getDescription());

        return mapper.map(
                repository.save(storage),
                StorageDTO.class
        );
    }

    @Override
    public StorageDTO create(CreatedStorageDTO storage) {
        return mapper.map(
                repository.save(
                        mapper.map(storage, Storage.class)
                ),
                StorageDTO.class
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PagingResponseDTO findAllWithParts(PagingRequestDTO request) {
        Page<StorageWithPartsDTO> storages = repository
                .findAllWithParts(PageRequest.of(request.getIndex(), request.getSize()))
                .map(value -> mapper.map(value, StorageWithPartsDTO.class));

        return PagingResponseDTO.builder()
                .content(storages.getContent())
                .index(storages.getNumber())
                .size(storages.getSize())
                .totalElement(storages.getTotalElements())
                .totalPage(storages.getTotalPages())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public StorageWithPartsDTO findStorageWithPartsById(UUID id) {

        return mapper.map(
                repository.findStorageWithPartsById(id),
                StorageWithPartsDTO.class
        );
    }


    @Override
    public StorageWithPartsDTO addParts(UUID id, List<UUID> partIds) {
        Storage storage = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(STORAGE_NOT_FOUND)
                );
        List<PartDetails> partDetails = partDetailsService.findAllIds(partIds);
        partDetails.forEach(storage::addPart);

        return mapper.map(
                repository.save(storage),
                StorageWithPartsDTO.class
        );
    }

    @Override
    public StorageWithPartsDTO removeParts(UUID id, List<UUID> partIds) {
        Storage storage = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(STORAGE_NOT_FOUND)
                );

        List<PartDetails> partDetails = partDetailsService.findAllIds(partIds);

        partDetails.forEach(storage::removePart);

        return mapper.map(
                repository.save(storage),
                StorageWithPartsDTO.class
        );
    }

    @Override
    public StorageDTO changeStatus(UUID id, StorageStatus status) {
        Storage storage = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(STORAGE_NOT_FOUND)
                );

        storage.changeStatus(status);

        return mapper.map(
                repository.save(storage),
                StorageDTO.class
        );
    }
}
