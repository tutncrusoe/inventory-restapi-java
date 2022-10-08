package com.group.inventory.user.boundary;

import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.user.dto.ImageDTO;
import com.group.inventory.user.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageRestResource {

    @Autowired
    private ImageService imageService;

    @PostMapping("/save")
    public Object uploadFile(
            @RequestParam("file") MultipartFile file) {
        String filecode = imageService.save(file);

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setFileName(file.getName());
        imageDTO.setSize(file.getSize());
        imageDTO.setDownloadUri("/download/" + filecode);
        imageDTO.setFileCode(filecode);

        return ResponseHelper.getResponse(imageDTO, HttpStatus.OK);
    }

    @GetMapping("/{fileCode}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileCode) {
        Resource file = imageService.load(fileCode);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    @PostMapping("/{fileCode}/delete")
    public Object deleteFile(@PathVariable String fileCode) {
        imageService.delete(fileCode);
        return ResponseHelper.getResponse("Image is deleted successfully.", HttpStatus.OK);
    }
}
