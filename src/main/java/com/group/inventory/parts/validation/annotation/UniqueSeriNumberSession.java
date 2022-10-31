package com.group.inventory.parts.validation.annotation;

import com.group.inventory.parts.validation.validator.UniqueSeriNumberSessionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueSeriNumberSessionValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueSeriNumberSession {
    String message() default "{part-session.part-number.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
