package com.group.inventory.common.util;

import com.group.inventory.common.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

public class ResponseHelper {
    public static ResponseEntity<BaseResponse> getResponse(Object obj, HttpStatus status) {
        return new ResponseEntity(
                BaseResponse
                        .builder()
                        .content(obj)
                        .hasError(false)
                        .errors(Collections.emptyList())
                        .timestamp(DateTimeUtils.now())
                        .status(status.value()),
                status
        );
    }

    public static ResponseEntity<BaseResponse> getErrorResponse(BindingResult errors, HttpStatus status) {
        return new ResponseEntity(
                BaseResponse
                        .builder()
                        .content(null)
                        .hasError(true)
                        .errors(ErrorHelper.getAllError(errors))
                        .timestamp(DateTimeUtils.now())
                        .status(status.value()),
                status);
    }

    public static ResponseEntity<BaseResponse> getErrorResponse(String errors, HttpStatus status) {
        return new ResponseEntity(
                BaseResponse
                        .builder()
                        .content(null)
                        .hasError(true)
                        .errors(List.of(errors))
                        .timestamp(DateTimeUtils.now())
                        .status(status.value()),
                status);
    }

    public static ResponseEntity<BaseResponse> getErrorResponse(List<String> errors, HttpStatus status) {
        return new ResponseEntity(
                BaseResponse
                        .builder()
                        .content(null)
                        .hasError(true)
                        .errors(errors)
                        .timestamp(DateTimeUtils.now())
                        .status(status.value()),
                status);
    }
}
