package com.group.inventory.storage.service.impl;

import com.group.inventory.common.dto.PagingRequestDTO;
import com.group.inventory.common.dto.PagingResponseDTO;
import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.storage.dto.CreatedWarehouseDTO;
import com.group.inventory.storage.dto.WarehouseDTO;
import com.group.inventory.storage.dto.WarehouseWithLocationsDTO;
import com.group.inventory.storage.model.Location;
import com.group.inventory.storage.model.StorageStatus;
import com.group.inventory.storage.model.Warehouse;
import com.group.inventory.storage.repository.WarehouseRepository;
import com.group.inventory.storage.service.LocationService;
import com.group.inventory.storage.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.group.inventory.common.constants.ExceptionMessageConstants.WAREHOUSE_NOT_FOUND;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository repository;
    private final BaseMapper mapper;
    private final LocationService locationService;

    public WarehouseServiceImpl(WarehouseRepository repository, BaseMapper mapper, LocationService locationService) {
        this.repository = repository;
        this.mapper = mapper;
        this.locationService = locationService;
    }

    @Override
    public JpaRepository<Warehouse, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return mapper;
    }

    @Override
    public PagingResponseDTO findAllWithLocations(PagingRequestDTO request) {
        Page<WarehouseWithLocationsDTO> warehouses = repository
                .findAllWithLocations(PageRequest.of(request.getIndex(), request.getSize()))
                .map(value -> mapper.map(value, WarehouseWithLocationsDTO.class));

        return PagingResponseDTO.builder()
                .content(warehouses.getContent())
                .index(warehouses.getNumber())
                .size(warehouses.getSize())
                .totalElement(warehouses.getTotalElements())
                .totalPage(warehouses.getTotalPages())
                .build();
    }

    @Override
    public WarehouseWithLocationsDTO findWithLocationsById(UUID id) {
        Warehouse warehouse = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(WAREHOUSE_NOT_FOUND)
                );
        return mapper.map(
                warehouse,
                WarehouseWithLocationsDTO.class
        );
    }

    @Override
    public WarehouseDTO save(CreatedWarehouseDTO dto) {
        Warehouse warehouse = mapper.map(dto, Warehouse.class);

        // Set default
        warehouse.setStatus(StorageStatus.AVAILABLE);
        warehouse.setCurrentLocationQuantity(0);

        return mapper.map(
                repository.save(warehouse),
                WarehouseDTO.class
        );
    }

    @Override
    public WarehouseDTO update(UUID id, WarehouseDTO dto) {
        Warehouse warehouse = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(WAREHOUSE_NOT_FOUND)
                );

        warehouse.setName(dto.getName());
        warehouse.setDescription(dto.getDescription());
        warehouse.setAddress(dto.getAddress());
        warehouse.setCapacity(dto.getCapacity());
        warehouse.setStatus(dto.getStatus());

        return mapper.map(
                repository.save(warehouse),
                WarehouseDTO.class
        );
    }

    @Override
    public WarehouseWithLocationsDTO addLocations(UUID id, List<UUID> locationIds) {

        Warehouse warehouse = repository.findById(id).orElseThrow(
                () -> new InventoryBusinessException(WAREHOUSE_NOT_FOUND)
        );

        List<Location> locations = locationService.findAllIds(locationIds);

        locations.forEach(warehouse::addLocation);

        return mapper.map(
                repository.save(warehouse),
                WarehouseWithLocationsDTO.class
        );
    }

    @Override
    public WarehouseWithLocationsDTO removeLocations(UUID id, List<UUID> locationIds) {

        Warehouse warehouse = repository.findById(id).orElseThrow(
                () -> new InventoryBusinessException(WAREHOUSE_NOT_FOUND)
        );

        List<Location> locations = locationService.findAllIds(locationIds);

        locations.forEach(warehouse::removeLocation);

        return mapper.map(
                repository.save(warehouse),
                WarehouseWithLocationsDTO.class
        );
    }
}
