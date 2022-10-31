package com.group.inventory.parts.validation.validator;

import com.group.inventory.parts.model.PartDetails;
import com.group.inventory.parts.model.PartSession;
import com.group.inventory.parts.repository.PartDetailsRepository;
import com.group.inventory.parts.validation.annotation.UniqueSeriNumberPartDetails;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueSeriNumberPartDetailsValidator implements ConstraintValidator<UniqueSeriNumberPartDetails, String> {
    private String message;

    private final PartDetailsRepository partDetailsRepository;

    public UniqueSeriNumberPartDetailsValidator(PartDetailsRepository partDetailsRepository) {
        this.partDetailsRepository = partDetailsRepository;
    }

    @Override
    public void initialize(UniqueSeriNumberPartDetails constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String partNumber, ConstraintValidatorContext constraintValidatorContext) {
        Optional<PartDetails> partDetailsOpt = partDetailsRepository.findByPartNumber(partNumber);

        if (partDetailsOpt.isEmpty()) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
