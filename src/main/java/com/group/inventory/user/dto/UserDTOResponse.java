package com.group.inventory.user.dto;

import com.group.inventory.user.model.UserStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOResponse {
    private UUID id;
    private String username;
    private String email;
    private String avatar;
    private UserStatus status;
}
