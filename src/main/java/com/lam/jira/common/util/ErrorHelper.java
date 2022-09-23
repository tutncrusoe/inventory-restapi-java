package com.lam.jira.common.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorHelper {
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
}
