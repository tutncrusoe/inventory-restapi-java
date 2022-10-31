package com.group.inventory.parts.dto;

import com.group.inventory.parts.validation.annotation.UniquePartCategoryName;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartCategoryWithSessionDTO implements Serializable {
    private UUID id;

    @UniquePartCategoryName
    @NotBlank
    private String name;

    private String description;

    private Set<PartSessionDTO> partSessions;
}
