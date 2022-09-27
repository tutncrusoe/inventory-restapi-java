package com.group.inventory.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SignUpRequest {
    private String username;
    private String email;
    private String password;

    private MultipartFile file;
    private Long roleId;

    public  SignUpRequest() {}

    public SignUpRequest(String username, String email, String password, MultipartFile file, Long roleId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.file = file;
        this.roleId = roleId;
    }
}
