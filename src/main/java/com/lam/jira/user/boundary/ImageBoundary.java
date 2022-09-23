package com.lam.jira.user.boundary;

import com.lam.jira.payload.response.MessageResponse;
import com.lam.jira.payload.response.UploadImageResponse;
import com.lam.jira.user.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageBoundary {

    @Autowired
    private ImageService imageService;

    @PostMapping("/save")
    public ResponseEntity<UploadImageResponse> uploadFile(
            @RequestParam("file") MultipartFile file) {
        String filecode = imageService.save(file);

        UploadImageResponse response = new UploadImageResponse();
        response.setFileName(file.getName());
        response.setSize(file.getSize());
        response.setDownloadUri("/download/" + filecode);
        response.setFileCode(filecode);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{fileCode}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileCode) {
        Resource file = imageService.load(fileCode);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    @PostMapping("/{fileCode}/delete")
    public ResponseEntity<MessageResponse> deleteFile(@PathVariable String fileCode) {
        MessageResponse messageResponse = imageService.delete(fileCode);
        return ResponseEntity.ok(messageResponse);
    }
}
