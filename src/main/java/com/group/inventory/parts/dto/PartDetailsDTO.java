package com.group.inventory.parts.dto;

import com.group.inventory.action.model.Action;
import com.group.inventory.action.model.EActionStatus;
import com.group.inventory.parts.model.EPartStatus;
import com.group.inventory.parts.validation.annotation.UniqueSeriNumberPartDetails;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartDetailsDTO implements Serializable {
    private UUID id;

    @NotBlank
    private String name;

    private String description;

    private String photo;

    private String madeBy;

    @UniqueSeriNumberPartDetails
    private String partNumber;

    private boolean isSpecial = false;

    private long quantity;

    @Enumerated(EnumType.STRING)
    private EPartStatus partStatus;

    @Enumerated(EnumType.STRING)
    private EActionStatus actionStatus;
}
