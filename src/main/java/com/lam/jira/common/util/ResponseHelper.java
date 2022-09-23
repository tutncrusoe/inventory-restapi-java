package com.lam.jira.common.util;

import com.lam.jira.common.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseHelper {
    public static ResponseEntity<Object> getResponse(Object obj, HttpStatus status) {
        BaseResponse<Object> baseResponse = new BaseResponse<>();

        baseResponse.setContent(obj);
        baseResponse.setHasError(false);
        baseResponse.setTimestamp(LocalDateTime.now());
        baseResponse.setStatus(HttpStatus.valueOf(status.value()));

        return new ResponseEntity<Object>(baseResponse, status);
    }

    public static ResponseEntity<Object> getErrorResponse(BindingResult errors, HttpStatus status) {
        BaseResponse<Object> baseResponse = new BaseResponse<>();

        baseResponse.setContent(null);
        baseResponse.setHasError(true);
        baseResponse.setErrors(ErrorHelper.getAllError(errors));
        baseResponse.setTimestamp(LocalDateTime.now());
        baseResponse.setStatus(HttpStatus.valueOf(status.value()));

        return new ResponseEntity<Object>(baseResponse, status);
    }

    public static ResponseEntity<Object> getErrorResponse(String errors, HttpStatus status) {
        BaseResponse<Object> baseResponse = new BaseResponse<>();

        baseResponse.setContent(null);
        baseResponse.setHasError(true);
        baseResponse.setErrors(List.of(errors));
        baseResponse.setTimestamp(LocalDateTime.now());
        baseResponse.setStatus(HttpStatus.valueOf(status.value()));

        return new ResponseEntity<Object>(baseResponse, status);
    }
}
