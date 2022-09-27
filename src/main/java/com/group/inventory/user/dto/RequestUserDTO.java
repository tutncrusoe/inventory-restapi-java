package com.group.inventory.user.dto;

import com.group.inventory.user.model.UserStatus;
import com.group.inventory.user.validation.annotation.UniqueEmail;
import com.group.inventory.user.validation.annotation.UniqueUsername;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RequestUserDTO {
    @UniqueUsername
    @Length(max = 100)
    @NotNull
    private String username;

    @UniqueEmail
    @NotNull
    @Email
    private String email;

    @NotBlank
    private String password;

    private MultipartFile file;

    @NotNull
    private String displayName;

    private String firstName;

    private String lastName;

    private String avatar;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status;

    @NotBlank
    private String departmentId;

    private String major;

    @Size(min=1, message="You must choose at least 1 role")
    private List<String> roleIdList;
}
