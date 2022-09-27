package com.group.inventory.common.exception;

import com.group.inventory.common.dto.BaseResponse;
import com.group.inventory.common.util.ErrorHelper;
import com.group.inventory.common.util.ResponseHelper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException) {
        return ResponseHelper.getErrorResponse(
                ErrorHelper.getError(constraintViolationException),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        return ResponseHelper.getErrorResponse(
                ErrorHelper.getError(methodArgumentNotValidException),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(
            EmptyResultDataAccessException emptyResultDataAccessException) {
        return ResponseHelper.getErrorResponse(
                ErrorHelper.getError(emptyResultDataAccessException),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(
            EntityNotFoundException entityNotFoundException) {
        return ResponseHelper.getErrorResponse(
                ErrorHelper.getError(entityNotFoundException),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleIndexOutOfBoundsException(
            IndexOutOfBoundsException indexOutOfBoundsException) {
        return ResponseHelper.getErrorResponse(
                ErrorHelper.getError(indexOutOfBoundsException),
                HttpStatus.BAD_REQUEST
        );
    }
}
