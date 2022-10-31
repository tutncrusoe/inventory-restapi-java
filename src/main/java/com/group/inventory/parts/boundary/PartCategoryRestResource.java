package com.group.inventory.parts.boundary;

import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.parts.dto.PartCategoryDTO;
import com.group.inventory.parts.model.PartCategory;
import com.group.inventory.parts.service.PartCategoryService;
import org.springframework.cache.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/part-category")
@EnableCaching
public class PartCategoryRestResource {

    private final PartCategoryService partCategoryService;

    public PartCategoryRestResource(PartCategoryService partCategoryService) {
        this.partCategoryService = partCategoryService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseHelper.getResponse(partCategoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/include")
    public ResponseEntity<?> findAllIncludeSessions() {
        return ResponseHelper.getResponse(partCategoryService.findAllIncludeSessions(), HttpStatus.OK);
    }

    // 2. FindBy
    @GetMapping("/{part-category-id}")
    public ResponseEntity<?> findById(@PathVariable("part-category-id") UUID id) {
        PartCategoryDTO partCategoryDTO = partCategoryService.findByIdDTO(id, PartCategoryDTO.class);
        return ResponseHelper.getResponse(partCategoryDTO, HttpStatus.OK);
    }

    // 3. save
    @PostMapping
    public Object savePartCategory(@RequestBody @Valid PartCategoryDTO partCategoryDTO) {
        return ResponseHelper.getResponse(
                partCategoryService.save(partCategoryDTO, PartCategory.class, PartCategoryDTO.class), HttpStatus.CREATED);
    }

    // 4. update
    @PutMapping("/{part-category-id}")
    public ResponseEntity<?> updatePartCategory(@PathVariable("part-category-id") UUID id, @RequestBody PartCategoryDTO partCategoryDTO) {
        return ResponseHelper.getResponse(partCategoryService.update(
                partCategoryDTO, id, PartCategoryDTO.class), HttpStatus.ACCEPTED);
    }

    // 4. delete
    @DeleteMapping
    public ResponseEntity<?> deletePartCategory(@RequestParam("part-category-id") UUID id) {
        partCategoryService.deleteById(id);
        return ResponseHelper.getResponse("Part category is deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/{part-category-id}/add-part-session")
    public ResponseEntity<?> addSessions(
            @PathVariable("part-category-id") UUID id,
            @RequestBody List<UUID> partSessionUUIDList) {
        return ResponseHelper.getResponse(partCategoryService.addPartSession(id, partSessionUUIDList), HttpStatus.OK);
    }

    @PostMapping("/{part-category-id}/remove-part-session")
    public ResponseEntity<?> removeSessions(
            @PathVariable("part-category-id") UUID id,
            @RequestBody List<UUID> partSessionUUIDList) {
        return ResponseHelper.getResponse(partCategoryService.removePartSession(id, partSessionUUIDList), HttpStatus.OK);
    }
}
