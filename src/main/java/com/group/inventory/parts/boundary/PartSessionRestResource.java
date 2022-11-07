package com.group.inventory.parts.boundary;

import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.parts.dto.PartSessionDTO;
import com.group.inventory.parts.model.PartSession;
import com.group.inventory.parts.service.PartSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
    @RequestMapping("api/v1/part-session")
public class PartSessionRestResource {
    private final PartSessionService partSessionService;

    public PartSessionRestResource(PartSessionService partSessionService) {
        this.partSessionService = partSessionService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseHelper.getResponse(partSessionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/include")
    public ResponseEntity<?> findAllIncludeCategoryAndDetails() {
        return ResponseHelper.getResponse(partSessionService.findAllIncludeCategoryAndDetails(), HttpStatus.OK);
    }

    // 2. FindBy
    @GetMapping("/{part-session-id}")
    public ResponseEntity<?> findById(@PathVariable("part-session-id") UUID id) {
        PartSessionDTO partSessionDTO = partSessionService.findByIdDTO(id, PartSessionDTO.class);
        return ResponseHelper.getResponse(partSessionDTO, HttpStatus.OK);
    }

    // 3. save
    @PostMapping
    public ResponseEntity<?> savePartSession(@Valid @RequestBody PartSessionDTO partSessionDTO) {
        return ResponseHelper.getResponse(
                partSessionService.save(partSessionDTO, PartSession.class, PartSessionDTO.class), HttpStatus.CREATED);
    }

    // 4. update
    @PutMapping("/{part-session-id}")
    public ResponseEntity<?> updatePartSession(@PathVariable("part-session-id") UUID id, @RequestBody PartSessionDTO partSessionDTO) {
        return ResponseHelper.getResponse(partSessionService.update(
                partSessionDTO, id, PartSessionDTO.class), HttpStatus.ACCEPTED);
    }

    // 4. delete
    @DeleteMapping
    public ResponseEntity<?> deletePartSession(@RequestParam("id") UUID id) {
        partSessionService.deleteById(id);
        return ResponseHelper.getResponse("Part session is deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/{part-session-id}/add-part-details")
    public ResponseEntity<?> addDetails(
            @PathVariable("part-session-id") UUID id,
            @RequestBody List<UUID> partDetailsUUIDList) {

        return ResponseHelper.getResponse(partSessionService.addPartDetails(id, partDetailsUUIDList), HttpStatus.OK);
    }

    @PostMapping("/{part-session-id}/remove-part-details")
    public ResponseEntity<?> removeDetails(
            @PathVariable("part-session-id") UUID id,
            @RequestBody List<UUID> partDetailsUUIDList) {
        return ResponseHelper.getResponse(partSessionService.removePartDetails(id, partDetailsUUIDList), HttpStatus.OK);
    }
}
