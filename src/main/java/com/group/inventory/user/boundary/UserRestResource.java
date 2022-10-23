package com.group.inventory.user.boundary;

import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.user.dto.UserDTORequest;
import com.group.inventory.user.model.UserStatus;
import com.group.inventory.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestResource {

    private final UserService userService;

    public UserRestResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Object findAllUser(HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.findAllUser(request), HttpStatus.OK);
    }

    @GetMapping("/{user-id}")
    public Object findUserById(@PathVariable("user-id") UUID userId, HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.findUserById(userId, request), HttpStatus.OK);
    }

    @GetMapping("/include-roles")
    public Object findUserWithRoles(HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.findUserWithRoles(request), HttpStatus.OK);
    }

    @GetMapping("/{user-id}/include-roles")
    public Object findUserWithRolesById(@PathVariable("user-id") UUID userId, HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.findUserWithRolesById(userId, request), HttpStatus.OK);
    }

    @PostMapping(value = "/sign-up")
    public Object signUp(@RequestBody @Valid UserDTORequest userDTORequest, HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.createNewUser(userDTORequest, request), HttpStatus.OK);
    }

    @PostMapping("{user-id}/add-roles")
    public Object addRoles(
            @PathVariable("user-id") UUID userId,
            @RequestBody List<UUID> roleUuids,
            HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.addRoles(userId, roleUuids, request), HttpStatus.OK);
    }

    @PostMapping("/{user-id}/remove-roles")
    public Object removeRoles(
            @PathVariable("user-id") UUID userId,
            @RequestBody List<UUID> roleUuids,
            HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.removeRoles(userId, roleUuids, request), HttpStatus.OK);
    }

    @PutMapping("/{user-id}")
    public Object updateUser(
            @PathVariable("user-id") UUID userId,
            @RequestBody @Valid UserDTORequest userDTORequest,
            HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.update(userId, userDTORequest, request), HttpStatus.OK);
    }

    @PutMapping("/{user-id}/user-image")
    public Object changeAvatar(
            @PathVariable("user-id") UUID userId,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.updateAvatar(userId, file, request), HttpStatus.OK);
    }

    @PutMapping("/{user-id}/change-status")
    public Object changeStatus(
            @PathVariable("user-id") UUID userId,
            @RequestParam("status") UserStatus userStatus,
            HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.changeStatus(userId, userStatus, request), HttpStatus.OK);
    }
}
