package com.group.inventory.parts.validation.validator;

import com.group.inventory.parts.model.PartCategory;
import com.group.inventory.parts.model.PartSession;
import com.group.inventory.parts.repository.PartSessionRepository;
import com.group.inventory.parts.validation.annotation.UniquePartSessionName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniquePartSessionValidator implements ConstraintValidator<UniquePartSessionName, String> {
    private String message;

    private final PartSessionRepository partSessionRepository;

    public UniquePartSessionValidator(PartSessionRepository partSessionRepository) {
        this.partSessionRepository = partSessionRepository;
    }

    @Override
    public void initialize(UniquePartSessionName constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        Optional<PartSession> partSessionOpt = partSessionRepository.findByName(name);

        if (partSessionOpt.isEmpty()) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
