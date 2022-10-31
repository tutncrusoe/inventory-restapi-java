package com.group.inventory.parts.validation.validator;

import com.group.inventory.parts.model.PartCategory;
import com.group.inventory.parts.repository.PartCategoryRepository;
import com.group.inventory.parts.validation.annotation.UniquePartCategoryName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniquePartCategoryValidator implements ConstraintValidator<UniquePartCategoryName, String> {
    private String message;

    private final PartCategoryRepository partCategoryRepository;

    public UniquePartCategoryValidator(PartCategoryRepository partCategoryRepository) {
        this.partCategoryRepository = partCategoryRepository;
    }

    @Override
    public void initialize(UniquePartCategoryName constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        Optional<PartCategory> partCategoryOpt = partCategoryRepository.findByName(name);

        if (partCategoryOpt.isEmpty()) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
