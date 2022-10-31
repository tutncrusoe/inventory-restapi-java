package com.group.inventory.common.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
public class BaseResponse {
    private Object content;
    private boolean hasError;
    private List<String> errors;
    private String timestamp;
    private int status;
}
