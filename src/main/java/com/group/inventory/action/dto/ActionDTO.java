package com.group.inventory.action.dto;

import com.group.inventory.action.model.EActionStatus;
import com.group.inventory.parts.dto.PartDetailsDTO;
import com.group.inventory.parts.model.EPartStatus;
import com.group.inventory.user.dto.UserDTOResponse;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO {
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EActionStatus name;

    @NotBlank(message = "{desc.blank}")
    private String description;

    private String photo;

    @Enumerated(EnumType.STRING)
    private EPartStatus partStatus;

    private boolean isSpecialPart;

    private long quantityUsed;

    private PartDetailsDTO partDetails;

    private UserDTOResponse user;
}
