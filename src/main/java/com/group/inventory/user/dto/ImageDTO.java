package com.group.inventory.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private String fileName;
    private String downloadUri;
    private String fileCode;
    private long size;
}
