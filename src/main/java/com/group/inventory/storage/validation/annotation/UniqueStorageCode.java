package com.group.inventory.storage.validation.annotation;

import com.group.inventory.storage.validation.validator.UniqueStorageCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueStorageCodeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueStorageCode {
    String message() default "{storage.code.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
