package com.group.inventory.storage.validation.annotation;


import com.group.inventory.storage.validation.validator.LocationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LocationValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LocationValid {
    String message() default "{location.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
