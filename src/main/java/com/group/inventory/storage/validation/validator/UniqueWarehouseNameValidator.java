package com.group.inventory.storage.validation.validator;

import com.group.inventory.storage.repository.WarehouseRepository;
import com.group.inventory.storage.validation.annotation.UniqueWarehouseName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueWarehouseNameValidator implements ConstraintValidator<UniqueWarehouseName, String> {
    private String message;
    private final WarehouseRepository repository;

    public UniqueWarehouseNameValidator(WarehouseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueWarehouseName constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (repository.findByName(code).isEmpty()) return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
