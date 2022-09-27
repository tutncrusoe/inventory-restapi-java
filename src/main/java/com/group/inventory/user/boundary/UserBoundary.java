package com.group.inventory.user.boundary;

import com.group.inventory.payload.response.MessageResponse;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.repository.RoleRepository;
import com.group.inventory.user.dto.RequestUserDTO;
import com.group.inventory.user.dto.UserDTO;
import com.group.inventory.user.service.ImageService;
import com.group.inventory.user.service.UserService;
import com.group.inventory.common.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserBoundary {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public Object getAllUser(HttpServletRequest request) {
        return ResponseHelper.getResponse(userService.findAllUser(request), HttpStatus.OK);
    }

    @GetMapping("/{user-id}")
    public Object getUser(@PathVariable("user-id") String id, HttpServletRequest request) {
        UserDTO userDTO = userService.findUserById(id, request);
        if (userDTO == null) {
            return ResponseHelper.getErrorResponse("User is not existed.", HttpStatus.BAD_REQUEST);
        }
        return ResponseHelper.getResponse(userDTO, HttpStatus.OK);
    }

    @PutMapping("/user-image/{user-id}")
    public Object changeAvatar(@PathVariable("user-id") String id, @RequestParam("file") MultipartFile file) {
        UserDTO userDTO = userService.updateAvatar(id, file);
        if (userDTO == null) {
            return ResponseHelper.getErrorResponse("User is not existed.", HttpStatus.BAD_REQUEST);
        }
        return ResponseHelper.getResponse(userDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/sign-up",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object signUp(@Valid @RequestBody RequestUserDTO requestUserDTO) {
        MultipartFile file = requestUserDTO.getFile();
        if (!file.isEmpty()){
            String filecode = imageService.save(file);
            requestUserDTO.setAvatar(filecode);
        }

        userService.createNewUser(requestUserDTO);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping("/{user-id}")
    public Object updateUser(@PathVariable("user-id") String id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseHelper.getResponse(userService.update(id, userDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public Object deleteUser(@RequestParam("id") String id) {
        userService.delete(id);
        return ResponseHelper.getResponse("User is deleted successfully", HttpStatus.OK);
    }

}
