package com.group.inventory.storage.validation.annotation;

import com.group.inventory.storage.validation.validator.UniqueLocationNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueLocationNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueLocationName {
    String message() default "{location.name.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
