package com.group.inventory.security.validation.validator;

import com.group.inventory.security.validation.annotation.ExistedUser;
import com.group.inventory.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistedUserValidator implements ConstraintValidator<ExistedUser, String> {
    private String message;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(ExistedUser constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return false;
        }

        boolean existedUser = userRepository.existsByUsername(username);

        if (!existedUser) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return existedUser;
    }
}
