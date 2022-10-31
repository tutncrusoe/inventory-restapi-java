package com.group.inventory.parts.validation.validator;

import com.group.inventory.parts.model.PartSession;
import com.group.inventory.parts.repository.PartSessionRepository;
import com.group.inventory.parts.validation.annotation.UniqueSeriNumberSession;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueSeriNumberSessionValidator implements ConstraintValidator<UniqueSeriNumberSession, String> {
    private String message;

    private PartSessionRepository partSessionRepository;

    public UniqueSeriNumberSessionValidator(PartSessionRepository partSessionRepository) {
        this.partSessionRepository = partSessionRepository;
    }

    @Override
    public void initialize(UniqueSeriNumberSession constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String partNumber, ConstraintValidatorContext constraintValidatorContext) {
        Optional<PartSession> partSessionOpt = partSessionRepository.findByPartNumber(partNumber);

        if (partSessionOpt.isEmpty()) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
