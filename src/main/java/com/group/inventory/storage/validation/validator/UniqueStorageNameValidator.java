package com.group.inventory.storage.validation.validator;

import com.group.inventory.storage.repository.StorageRepository;
import com.group.inventory.storage.validation.annotation.UniqueStorageName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueStorageNameValidator implements ConstraintValidator<UniqueStorageName, String> {
    private String message;
    private final StorageRepository repository;

    public UniqueStorageNameValidator(StorageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueStorageName constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (repository.findByName(name).isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
