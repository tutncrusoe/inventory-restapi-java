package com.group.inventory.parts.boundary;

import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.parts.dto.PartDetailsDTO;
import com.group.inventory.parts.model.PartDetails;
import com.group.inventory.parts.service.PartDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/part-details")
public class PartDetailsRestResource {
    private final PartDetailsService partDetailsService;

    public PartDetailsRestResource(PartDetailsService partDetailsService) {
        this.partDetailsService = partDetailsService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseHelper.getResponse(partDetailsService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/include")
    public ResponseEntity<?> findAllIncludeSession(HttpServletRequest request) {
        return ResponseHelper.getResponse(partDetailsService.findAllIncludeSession(request), HttpStatus.OK);
    }

    // 2. FindBy
    @GetMapping("/{part-details-id}")
    public ResponseEntity<?> findById(@PathVariable("part-details-id") UUID id) {
        PartDetailsDTO partDetailsDTO = partDetailsService.findByIdDTO(id, PartDetailsDTO.class);
        return ResponseHelper.getResponse(partDetailsDTO, HttpStatus.OK);
    }

    @GetMapping("/{part-details-id}/include")
    public ResponseEntity<?> findByIdInclude(@PathVariable("part-details-id") UUID id, HttpServletRequest request) {
        return ResponseHelper.getResponse(partDetailsService.findByIdDTOInclude(id, request), HttpStatus.OK);
    }

    // 3. save
    @PostMapping
    public ResponseEntity<?> savePartDetails(@Valid @RequestBody PartDetailsDTO partDetailsDTO) {
        return ResponseHelper.getResponse(
                partDetailsService.save(partDetailsDTO, PartDetails.class, PartDetailsDTO.class), HttpStatus.CREATED);
    }

    // 4. update
    @PutMapping("/{part-details-id}")
    public ResponseEntity<?> updatePartDetails(@PathVariable("part-details-id") UUID id, @Valid @RequestBody PartDetailsDTO partDetailsDTO) {
        return ResponseHelper.getResponse(partDetailsService.update(
                partDetailsDTO, id, PartDetailsDTO.class), HttpStatus.ACCEPTED);
    }

    // 4. delete
    @DeleteMapping
    public ResponseEntity<?> deletePartDetails(@RequestParam("id") UUID id) {
        partDetailsService.deleteById(id);
        return ResponseHelper.getResponse("Part details is deleted successfully", HttpStatus.OK);
    }
}
