package com.group.inventory.user.dto;

import com.group.inventory.user.model.UserStatus;
import com.group.inventory.user.validation.annotation.UniqueEmail;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTORequest {

    @Length(min = 1, max = 100, message = "{name.length}")
    @NotNull
    private String username;

    @UniqueEmail
    @NotNull
    @Email
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status;
}
