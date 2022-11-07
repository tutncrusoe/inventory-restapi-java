package com.group.inventory.storage.validation.annotation;

import com.group.inventory.storage.validation.validator.StorageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StorageValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StorageValid {
    String message() default "{storage.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
