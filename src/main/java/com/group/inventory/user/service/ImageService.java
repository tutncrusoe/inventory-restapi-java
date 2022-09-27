package com.group.inventory.user.service;

import com.group.inventory.common.util.MethodSup;
import com.group.inventory.payload.response.MessageResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

public interface ImageService {
    void init();
    String save(MultipartFile file);
    Resource load(String fileCode);
    void delete(String fileCode);

    String generateImgUrl(String imageCode, HttpServletRequest request) throws MalformedURLException;
}

@Service
class ImageServiceImpl implements ImageService {
    private final Path root = Paths.get("src/main/resources/static/images");
    String foundFile = null;

    @Value("${image.get.path}")
    private String originImagePath;

    public ImageServiceImpl() {
        init();
    }

    @Override
    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create repository " + e.getMessage());
        }
    }

    @Override
    public String save(MultipartFile file) {
        try {
            String fileCode = RandomStringUtils.randomAlphanumeric(8);
            Path filePath = root.resolve(fileCode + "-" + file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);
            return fileCode;
        } catch (Exception e) {
            throw new RuntimeException("Could not save file!");
        }
    }

    @Override
    public Resource load(String fileCode) {
        try {
            Files.list(root).forEach(file -> {
                if (file.getFileName().toString().startsWith(fileCode)) {
                    foundFile = file.getFileName().toString();
                    return;
                }
            });
            Path file = root.resolve(foundFile);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(String fileCode) {
        try {
            Files.list(root).forEach(file -> {
                if (file.getFileName().toString().startsWith(fileCode)) {
                    try {
                        Files.deleteIfExists(file.getRoot());
                    } catch (IOException e) {
                        throw new IndexOutOfBoundsException("Could not delete file with" + fileCode);
                    }
                }
            });
        } catch (NoSuchFileException e) {
            throw new RuntimeException(
                    "No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            throw new RuntimeException("Directory is not empty.");
        } catch (IOException e) {
            throw new RuntimeException("Invalid permissions.");
        }
    }

    @Override
    public String generateImgUrl(String imageCode, HttpServletRequest request) throws MalformedURLException {
        return MethodSup.getURLBase(request) + originImagePath + "/" + imageCode;
    }
}