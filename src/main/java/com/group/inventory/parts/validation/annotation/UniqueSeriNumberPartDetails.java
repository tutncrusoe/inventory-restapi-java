package com.group.inventory.parts.validation.annotation;

import com.group.inventory.parts.validation.validator.UniqueSeriNumberPartDetailsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueSeriNumberPartDetailsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueSeriNumberPartDetails {
    String message() default "{part-details.part-number.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
