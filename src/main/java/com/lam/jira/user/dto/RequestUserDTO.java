package com.lam.jira.user.dto;

import antlr.LexerSharedInputState;
import com.lam.jira.user.model.UserEntity;
import com.lam.jira.user.model.UserStatus;
import com.lam.jira.user.validation.annotation.UniqueEmail;
import com.lam.jira.user.validation.annotation.UniqueUsername;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    private String department;

    private String major;

    private List<String> roleIdList;
}
