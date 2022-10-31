package com.group.inventory.action.dto;

import com.group.inventory.action.model.EActionStatus;
import com.group.inventory.parts.model.EPartStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTOWithFileRequest {
    private UUID userId;

    private UUID partDetailsId;

    @Enumerated(EnumType.STRING)
    private EActionStatus name;

    @NotBlank(message = "{desc.blank}")
    private String description;

    @Enumerated(EnumType.STRING)
    private EPartStatus partStatus;

    // Use when part details is special
    private long quantityUsed;

    @NotNull(message = "{photo.null}")
    private MultipartFile file;
}
