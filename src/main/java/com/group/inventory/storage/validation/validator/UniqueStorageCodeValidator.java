package com.group.inventory.storage.validation.validator;

import com.group.inventory.storage.repository.StorageRepository;
import com.group.inventory.storage.validation.annotation.UniqueStorageCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueStorageCodeValidator implements ConstraintValidator<UniqueStorageCode, String> {
    private String message;
    private final StorageRepository repository;

    public UniqueStorageCodeValidator(StorageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueStorageCode constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (repository.findByCode(code).isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
