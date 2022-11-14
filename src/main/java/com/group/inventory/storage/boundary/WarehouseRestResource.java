package com.group.inventory.storage.boundary;

import com.group.inventory.common.dto.PagingRequestDTO;
import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.storage.dto.CreatedWarehouseDTO;
import com.group.inventory.storage.dto.WarehouseDTO;
import com.group.inventory.storage.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseRestResource {
    private final WarehouseService warehouseService;

    public WarehouseRestResource(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public Object getAll(PagingRequestDTO request) {
        return ResponseHelper.getResponse(
                warehouseService.findAll(request, WarehouseDTO.class),
                HttpStatus.OK
        );
    }

    @GetMapping("/include-locations")
    public Object getAllWithLocations(PagingRequestDTO request) {
        return ResponseHelper.getResponse(
                warehouseService.findAllWithLocations(request),
                HttpStatus.OK
        );
    }

    @GetMapping("/{warehouse-id}")
    public Object getById(@PathVariable("warehouse-id") UUID id) {
        return ResponseHelper.getResponse(
                warehouseService.findByIdDTO(id, WarehouseDTO.class),
                HttpStatus.OK
        );
    }

    @GetMapping("/{warehouse-id}/include-locations")
    public Object getWarehouseWithLocationsById(@PathVariable("warehouse-id") UUID id) {
        return ResponseHelper.getResponse(
                warehouseService.findWithLocationsById(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public Object save(@RequestBody @Valid CreatedWarehouseDTO dto) {
        return ResponseHelper.getResponse(
                warehouseService.save(dto),
                HttpStatus.OK
        );
    }

    @PutMapping("/{warehouse-id}")
    public Object update(@PathVariable("warehouse-id") UUID id,
                         @RequestBody @Valid WarehouseDTO dto) {
        return ResponseHelper.getResponse(
                warehouseService.update(id, dto),
                HttpStatus.OK
        );
    }

    @PostMapping("/{warehouse-id}/add-locations")
    public Object addLocation(@PathVariable("warehouse-id") UUID id,
                              @RequestBody List<UUID> locationIds) {
        return ResponseHelper.getResponse(
                warehouseService.addLocations(id, locationIds),
                HttpStatus.OK
        );
    }

    @PostMapping("/{warehouse-id}/remove-locations")
    public Object removeLocation(@PathVariable("warehouse-id") UUID id,
                                 @RequestBody List<UUID> locationIds) {
        return ResponseHelper.getResponse(
                warehouseService.removeLocations(id, locationIds),
                HttpStatus.OK
        );
    }
}
