package com.group.inventory.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class ErrorHelper {
    public static final String DEFAULT_UNEXPECTED_MESSAGE = "Ops! Something went wrong...";
    public static List<String> getAllError(BindingResult result) {
        Map<String, String> errors= new HashMap<>();
        List<String> errList = new ArrayList<>();

        result.getAllErrors().forEach(err -> {
            errors.put(err.getObjectName(), err.getDefaultMessage());
        });

        for (String key: errors.keySet()) {
            errList.add("Error in: " + key + ", Reason: " + errors.get(key));
        }

        return errList;
    }

    public static List<String> getError(ConstraintViolationException constraintViolationException) {
        return constraintViolationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    public static List<String> getError(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }

    public static List<String> getError(RuntimeException exception) {
        return List.of(exception.getMessage());
    }

    public static List<String> getError(ServletException exception) {
        return List.of(exception.getMessage());
    }
}
