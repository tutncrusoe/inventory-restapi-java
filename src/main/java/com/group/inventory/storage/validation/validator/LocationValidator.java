package com.group.inventory.storage.validation.validator;

import com.group.inventory.storage.dto.LocationDTO;
import com.group.inventory.storage.model.Location;
import com.group.inventory.storage.repository.LocationRepository;
import com.group.inventory.storage.validation.annotation.LocationValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;

public class LocationValidator implements ConstraintValidator<LocationValid, LocationDTO> {
    private String message;

    private final LocationRepository repository;

    public LocationValidator(LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(LocationValid constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(LocationDTO dto, ConstraintValidatorContext context) {
        Optional<Location> locationOptional = repository.findByName(dto.getName());
        if (locationOptional.isEmpty())
            return true;

        Location location = locationOptional.get();
        if (Objects.equals(location.getId(), dto.getId()))
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
