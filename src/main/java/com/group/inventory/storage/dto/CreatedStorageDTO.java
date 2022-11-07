package com.group.inventory.storage.dto;

import com.group.inventory.storage.validation.annotation.UniqueStorageCode;
import com.group.inventory.storage.validation.annotation.UniqueStorageName;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CreatedStorageDTO {
    @NotBlank(message = "{storage.name.blank}")
    @UniqueStorageName
    private String name;

    @NotBlank(message = "{storage.code.blank}")
    @UniqueStorageCode
    private String code;

    @Min(value = 0, message = "{volume.min}")
    private float volume;

    @NotBlank(message = "{storage.description.blank}")
    private String description;
}
