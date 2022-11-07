package com.group.inventory.storage.dto;

import com.group.inventory.storage.model.StorageStatus;
import com.group.inventory.storage.validation.annotation.StorageValid;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@StorageValid
public class StorageDTO {
    private UUID id;

    @NotBlank(message = "{storage.name.blank}")
    private String name;

    @NotBlank(message = "{storage.code.blank}")
    private String code;

    @Min(value = 0, message = "{volume.min}")
    private float volume;

    private float currentVolume;

    @NotNull(message = "{storage.status.null}")
    private StorageStatus status;

    @NotBlank(message = "{storage.description.blank}")
    private String description;
}
