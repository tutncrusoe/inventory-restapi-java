package com.lam.jira.user.boundary;

import com.lam.jira.common.util.ResponseHelper;
import com.lam.jira.payload.response.MessageResponse;
import com.lam.jira.role.repository.RoleRepository;
import com.lam.jira.user.dto.RequestUserDTO;
import com.lam.jira.user.service.ImageService;
import com.lam.jira.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserBoundary {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ImageService imageService;

    @Value("${image.get.path}")
    private String originImagePath;

    @PostMapping(value = "/sign-up",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object signUp(@Valid @RequestBody RequestUserDTO requestUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHelper.getErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);
        }

        MultipartFile file = requestUserDTO.getFile();
        if (!file.isEmpty()){
            String filecode = imageService.save(file);
            requestUserDTO.setAvatar(filecode);
        }

//        Resource resource = imageService.load(userDetails.getImageCode());
//
//        String base = MethodSup.getURLBase(request);
//
//        String imagePath = base + originImagePath + "/" + userDetails.getImageCode();

        userService.createNewUser(requestUserDTO);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
