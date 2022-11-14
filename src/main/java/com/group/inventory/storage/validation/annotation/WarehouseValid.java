package com.group.inventory.storage.validation.annotation;

import com.group.inventory.storage.validation.validator.WarehouseValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = WarehouseValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WarehouseValid {
    String message() default "{warehouse.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
