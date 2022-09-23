package com.lam.jira.user.validation.validator;

import com.lam.jira.user.repository.UserRepository;
import com.lam.jira.user.validation.annotation.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private String message;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
        this.message = uniqueEmail.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        boolean existedEmail = userRepository.existsByEmail(email);
        if (!existedEmail) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return existedEmail;
    }
}
