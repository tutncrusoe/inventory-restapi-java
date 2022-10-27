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
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTORequest {

    @Length(min = 1, max = 100, message = "{user.name.length}")
    @NotBlank(message = "{user.name.not_blank}")
    private String username;

    @UniqueEmail
    @NotBlank(message = "{user.email.not_blank}")
    @Email
    private String email;

    @NotBlank(message = "{user.password.not_blank}")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{user.status.not_null}")
    private UserStatus status;

    @NotNull(message = "{department.id.not_null}")
    private UUID departmentId;
}
