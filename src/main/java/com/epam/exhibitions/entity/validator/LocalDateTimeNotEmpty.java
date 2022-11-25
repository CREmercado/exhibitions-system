package com.epam.exhibitions.entity.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalDateTimeValidator.class)
@Documented
public @interface LocalDateTimeNotEmpty {
    String message() default "${validatedValue} must be present and be a valid datetime";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
