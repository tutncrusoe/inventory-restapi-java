package com.group.inventory.role.validation.validator;

import com.group.inventory.role.model.Role;
import com.group.inventory.role.repository.RoleRepository;
import com.group.inventory.role.validation.annotation.UniqueRoleCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueRoleCodeValidator implements ConstraintValidator<UniqueRoleCode, String> {
    private String message;

    private final RoleRepository roleRepository;

    public UniqueRoleCodeValidator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initialize(UniqueRoleCode constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Role> roleOpt = roleRepository.findByCode(code);

        if (roleOpt.isEmpty()) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
