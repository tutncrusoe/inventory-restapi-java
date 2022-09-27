package com.group.inventory.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class BaseResponse{
    private Object content;
    private boolean hasError;
    private List<String> errors;
    private String timestamp;
    private int status;
}
