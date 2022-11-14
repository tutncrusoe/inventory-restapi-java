package com.group.inventory.storage.boundary;

import com.group.inventory.common.dto.PagingRequestDTO;
import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.storage.dto.CreatedStorageDTO;
import com.group.inventory.storage.dto.StorageDTO;
import com.group.inventory.storage.model.StorageStatus;
import com.group.inventory.storage.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/storages")
public class StorageRestResource {
    private final StorageService storageService;

    public StorageRestResource(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public Object getAll(PagingRequestDTO request) {
        return ResponseHelper.getResponse(
                storageService.findAll(request, StorageDTO.class),
                HttpStatus.OK
        );
    }

    @GetMapping("/include-parts")
    public Object getAllWithParts(PagingRequestDTO request) {
        return ResponseHelper.getResponse(
                storageService.findAllWithParts(request),
                HttpStatus.OK
        );
    }

    @GetMapping("/{storage-id}/include-parts")
    public Object getStorageWithPartsById(@PathVariable("storage-id") UUID id) {
        return ResponseHelper.getResponse(
                storageService.findStorageWithPartsById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/{storage-id}")
    public Object getById(@PathVariable("storage-id") UUID id) {
        return ResponseHelper.getResponse(
                storageService.findByIdDTO(id, StorageDTO.class),
                HttpStatus.OK
        );
    }

    @PostMapping
    public Object create(@RequestBody @Valid CreatedStorageDTO storage) {
        return ResponseHelper.getResponse(
                storageService.create(storage),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{storage-id}")
    public Object update(@PathVariable("storage-id") UUID id,
            @RequestBody @Valid StorageDTO storage) {
        return ResponseHelper.getResponse(
                storageService.update(id, storage),
                HttpStatus.OK
        );
    }

    @PostMapping("/{storage-id}/add-parts")
    public Object addParts(@PathVariable("storage-id") UUID id,
                           @RequestBody List<UUID> partIds) {
        return ResponseHelper.getResponse(
                storageService.addParts(id, partIds),
                HttpStatus.OK
        );
    }

    @PostMapping("/{storage-id}/remove-parts")
    public Object removeParts(@PathVariable("storage-id") UUID id,
                              @RequestBody List<UUID> partIds) {
        return ResponseHelper.getResponse(
                storageService.removeParts(id, partIds),
                HttpStatus.OK
        );
    }

    // pass a status in request body : "UNAVAILABLE" or "AVAILABLE"
    @PutMapping("/{storage-id}/change-status")
    public Object changeStatus(@PathVariable("storage-id") UUID id,
                               @RequestBody StorageStatus status) {
        return ResponseHelper.getResponse(
                storageService.changeStatus(id, status),
                HttpStatus.OK
        );
    }
}
