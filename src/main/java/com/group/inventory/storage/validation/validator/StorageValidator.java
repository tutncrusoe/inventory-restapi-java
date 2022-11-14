package com.group.inventory.storage.validation.validator;

import com.group.inventory.storage.dto.StorageDTO;
import com.group.inventory.storage.model.Storage;
import com.group.inventory.storage.repository.StorageRepository;
import com.group.inventory.storage.validation.annotation.StorageValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;

public class StorageValidator implements ConstraintValidator<StorageValid, StorageDTO> {
    private String message;
    private final StorageRepository repository;

    public StorageValidator(StorageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(StorageValid constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(StorageDTO dto, ConstraintValidatorContext context) {
        if (isNotCodeExisted(dto) && isNotNameExisted(dto))
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }

    private boolean isNotNameExisted(StorageDTO dto){
        Optional<Storage> storageOptional = repository.findByName(dto.getName());
        if (storageOptional.isEmpty())
            return true;

        Storage storage = storageOptional.get();
        return Objects.equals(storage.getId(), dto.getId());
    }

    private boolean isNotCodeExisted(StorageDTO dto){
        Optional<Storage> storageOptional = repository.findByCode(dto.getCode());
        if (storageOptional.isEmpty())
            return true;

        Storage storage = storageOptional.get();
        return Objects.equals(storage.getId(), dto.getId());
    }
}
