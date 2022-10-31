package com.group.inventory.action.boundary;

import com.group.inventory.action.dto.ActionDTO;
import com.group.inventory.action.dto.ActionDTOWithFileRequest;
import com.group.inventory.action.service.ActionService;
import com.group.inventory.common.util.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/action")
public class ActionRestResource {
    private final ActionService actionService;


    public ActionRestResource(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        return ResponseHelper.getResponse(actionService.findAllDTO(request), HttpStatus.OK);
    }

    // 2. FindBy
    @GetMapping("/{action-id}")
    public ResponseEntity<?> findById(@PathVariable("action-id") UUID id, HttpServletRequest request) {
        return ResponseHelper.getResponse(actionService.findByIdDTO(id, request), HttpStatus.OK);
    }

    // 3. save
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> savePartDetails(
            @ModelAttribute @Valid ActionDTOWithFileRequest actionDTOWithFileRequest,
            HttpServletRequest request
    ) {
        return ResponseHelper.getResponse(
                actionService.createAction(actionDTOWithFileRequest, request), HttpStatus.CREATED);
    }

    // 4. update
    @PutMapping("/{action-id}/update-photo")
    public ResponseEntity<?> updatePhoto(
            @PathVariable("action-id") UUID id,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        return ResponseHelper.getResponse(actionService.updatePhoto(
                id, file, request), HttpStatus.ACCEPTED);
    }
}
