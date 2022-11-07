package com.group.inventory.storage.boundary;

import com.group.inventory.common.dto.PagingRequestDTO;
import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.storage.dto.CreatedLocationDTO;
import com.group.inventory.storage.dto.LocationDTO;
import com.group.inventory.storage.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationRestResource {

    private final LocationService locationService;


    public LocationRestResource(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public Object getAll(PagingRequestDTO request) {
        return ResponseHelper.getResponse(
                locationService.findAll(request, LocationDTO.class),
                HttpStatus.OK
        );
    }

    @GetMapping("/include-storages")
    public Object getAllWithStorages(PagingRequestDTO request) {
        return ResponseHelper.getResponse(
                locationService.findAllWithStorage(request),
                HttpStatus.OK
        );
    }

    @GetMapping("/{location-id}")
    public Object findById(@PathVariable("location-id") UUID id) {
        return ResponseHelper.getResponse(
                locationService.findByIdDTO(id, LocationDTO.class),
                HttpStatus.OK
        );
    }

    @GetMapping("/{location-id}/include-storages")
    public Object getLocationWithStoragesById(@PathVariable("location-id") UUID id) {
        return ResponseHelper.getResponse(
                locationService.findLocationWithStoragesById(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public Object create(@RequestBody @Valid CreatedLocationDTO dto) {
        return ResponseHelper.getResponse(
                locationService.save(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{location-id}")
    public Object update(@PathVariable("location-id") UUID id,
                         @RequestBody @Valid LocationDTO dto) {
        return ResponseHelper.getResponse(
                locationService.update(id,dto),
                HttpStatus.OK
        );
    }

    @PostMapping("/{location-id}/add-storages")
    public Object addStorages(@PathVariable("location-id") UUID id,
                              @RequestBody List<UUID> storageIds) {

        return ResponseHelper.getResponse(
                locationService.addStorages(id, storageIds),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{location-id}/remove-storages")
    public Object removeStorages(@PathVariable("location-id") UUID id,
                                 @RequestBody List<UUID> storageIds) {

        return ResponseHelper.getResponse(
                locationService.removeStorages(id, storageIds),
                HttpStatus.OK
        );
    }

}


