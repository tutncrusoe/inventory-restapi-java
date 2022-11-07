package com.group.inventory.storage.validation.validator;

import com.group.inventory.storage.repository.LocationRepository;
import com.group.inventory.storage.validation.annotation.UniqueLocationName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLocationNameValidator implements ConstraintValidator<UniqueLocationName, String> {
    private String message;
    private final LocationRepository repository;

    public UniqueLocationNameValidator(LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueLocationName constraintAnnotation) {
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
