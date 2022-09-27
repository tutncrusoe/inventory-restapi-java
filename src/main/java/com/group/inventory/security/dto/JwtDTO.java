package com.group.inventory.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class JwtDTO {
    private String jwt;

    private UUID id;

    private String displayName;

    private String username;

    private String email;

    private String avatar;
}
