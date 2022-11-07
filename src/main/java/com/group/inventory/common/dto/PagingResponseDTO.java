package com.group.inventory.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PagingResponseDTO {

    // This is data returned by service (DTO)
    private Object content;

    // Current page
    private int index;

    // Page size
    private int size;

    // Total page with this size
    private int totalPage;

    // Total element in result
    private long totalElement;

    // Response data was sorted by field.
    private String sortBy;
}
