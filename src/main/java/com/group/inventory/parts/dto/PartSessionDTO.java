package com.group.inventory.parts.dto;

import com.group.inventory.parts.validation.annotation.UniquePartSessionName;
import com.group.inventory.parts.validation.annotation.UniqueSeriNumberSession;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartSessionDTO implements Serializable {
    private UUID id;

    @UniquePartSessionName
    @NotBlank
    private String name;

    private String description;

    @UniqueSeriNumberSession
    private String partNumber;
}
