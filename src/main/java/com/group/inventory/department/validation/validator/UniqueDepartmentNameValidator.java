package com.group.inventory.department.validation.validator;

import com.group.inventory.department.model.Department;
import com.group.inventory.department.repository.DepartmentRepository;
import com.group.inventory.department.validation.annotation.UniqueDepartmentName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueDepartmentNameValidator implements ConstraintValidator<UniqueDepartmentName, String> {
    private String message;

    private final DepartmentRepository departmentRepository;

    public UniqueDepartmentNameValidator(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void initialize(UniqueDepartmentName constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Department> departmentOpt = departmentRepository.findByName(name);

        if (departmentOpt.isEmpty()) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
