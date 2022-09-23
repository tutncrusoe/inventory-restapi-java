package com.lam.jira.user.validation.validator;

import com.lam.jira.user.repository.UserRepository;
import com.lam.jira.user.validation.annotation.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private String message;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
       this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        boolean existedUsername = userRepository.existsByUsername(username);
        if (!existedUsername) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return existedUsername;
    }
}
