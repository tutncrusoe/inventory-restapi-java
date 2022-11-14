package com.group.inventory.common.dto;

import lombok.*;

import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PagingRequestDTO {
    // Default value for paging request
    // If request not has pagination information will return first page of data.

    // Because index must not less than 0 and size must not less 1

    // Current page, default value is 0
    @Min(0)
    private int index = 0;

    // Page size, default is 5
    @Min(1)
    private int size = 5;
}
