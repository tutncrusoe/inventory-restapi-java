package com.group.inventory.storage.service.impl;

import com.group.inventory.common.dto.PagingRequestDTO;
import com.group.inventory.common.dto.PagingResponseDTO;
import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.storage.dto.CreatedLocationDTO;
import com.group.inventory.storage.dto.LocationDTO;
import com.group.inventory.storage.dto.LocationWIthStorageDTO;
import com.group.inventory.storage.model.Location;
import com.group.inventory.storage.model.Storage;
import com.group.inventory.storage.model.StorageStatus;
import com.group.inventory.storage.repository.LocationRepository;
import com.group.inventory.storage.service.LocationService;
import com.group.inventory.storage.service.StorageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.group.inventory.common.constants.ExceptionMessageConstants.LOCATION_NOT_FOUND;

@Service
public class LocationServiceImpl implements LocationService {
    private final BaseMapper mapper;
    private final LocationRepository repository;

    private final StorageService storageService;

    public LocationServiceImpl(BaseMapper mapper, LocationRepository repository, StorageService storageService) {
        this.mapper = mapper;
        this.repository = repository;
        this.storageService = storageService;
    }

    @Override
    public JpaRepository<Location, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return mapper;
    }

    @Override
    public LocationDTO save(CreatedLocationDTO dto) {
        Location location = mapper.map(dto, Location.class);

        // Set default value
        location.setCurrentStorageQuantity(0);
        location.setStatus(StorageStatus.AVAILABLE);

        return mapper.map(
                repository.save(location),
                LocationDTO.class
        );
    }

    @Override
    public LocationDTO update(UUID id, LocationDTO dto) {
        Location location = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(LOCATION_NOT_FOUND)
                );

        location.setName(dto.getName());
        location.setDescription(dto.getDescription());
        location.setCapacity(dto.getCapacity());
        location.setStatus(dto.getStatus());

        return mapper.map(
                repository.save(location),
                LocationDTO.class
        );
    }

    @Override
    public LocationWIthStorageDTO addStorages(UUID id, List<UUID> storageIds) {
        Location location = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(LOCATION_NOT_FOUND)
                );

        List<Storage> storages = storageService.findAllIds(storageIds);

        storages.forEach(location::addStorage);

        return mapper.map(
                repository.save(location),
                LocationWIthStorageDTO.class
        );
    }

    @Override
    public LocationWIthStorageDTO removeStorages(UUID id, List<UUID> storageIds) {
        Location location = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(LOCATION_NOT_FOUND)
                );

        List<Storage> storages = storageService.findAllIds(storageIds);

        storages.forEach(location::removeStorage);

        return mapper.map(
                repository.save(location),
                LocationWIthStorageDTO.class
        );
    }

    @Override
    public PagingResponseDTO findAllWithStorage(PagingRequestDTO request) {
        Page<LocationWIthStorageDTO> locations = repository
                .findAllWithStorages(PageRequest.of(request.getIndex(), request.getSize()))
                .map(value -> mapper.map(value, LocationWIthStorageDTO.class));

        return PagingResponseDTO.builder()
                .content(locations.getContent())
                .index(locations.getNumber())
                .size(locations.getSize())
                .totalElement(locations.getTotalElements())
                .totalPage(locations.getTotalPages())
                .build();
    }

    @Override
    public LocationWIthStorageDTO findLocationWithStoragesById(UUID id) {
        return mapper.map(
                repository.findLocationWithStoragesById(id),
                LocationWIthStorageDTO.class
        );
    }
}
