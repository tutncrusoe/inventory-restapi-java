package com.group.inventory.user.validation.annotation;

import com.group.inventory.user.validation.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UniqueEmail {
    String message() default "{email.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
