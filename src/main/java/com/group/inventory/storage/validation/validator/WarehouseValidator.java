package com.group.inventory.storage.validation.validator;

import com.group.inventory.storage.dto.WarehouseDTO;
import com.group.inventory.storage.model.Warehouse;
import com.group.inventory.storage.repository.WarehouseRepository;
import com.group.inventory.storage.validation.annotation.WarehouseValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;

public class WarehouseValidator implements ConstraintValidator<WarehouseValid, WarehouseDTO> {

    private String message;

    private final WarehouseRepository repository;

    public WarehouseValidator(WarehouseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(WarehouseValid constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(WarehouseDTO dto, ConstraintValidatorContext context) {
        Optional<Warehouse> existedWarehouseByName = repository.findByName(dto.getName());
        if (existedWarehouseByName.isEmpty())
            return true;

        Warehouse warehouse = existedWarehouseByName.get();
        if (Objects.equals(warehouse.getId(), dto.getId()))
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
