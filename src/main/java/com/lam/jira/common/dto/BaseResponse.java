package com.lam.jira.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
    private T content;
    private boolean hasError;
    private List<String> errors;
    private LocalDateTime timestamp;
    private HttpStatus status;
}
