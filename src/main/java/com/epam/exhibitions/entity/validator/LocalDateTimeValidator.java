package com.epam.exhibitions.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class LocalDateTimeValidator implements ConstraintValidator<LocalDateTimeNotEmpty, LocalDateTime> {

    @Override
    public void initialize(LocalDateTimeNotEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        Boolean valid = false;
        LocalDateTime today = LocalDateTime.now();

        if(null != localDateTime && localDateTime.isBefore(today))
            valid = true;
        else if(null != localDateTime && localDateTime.isAfter(today))
            valid = true;
        else if (null != localDateTime && localDateTime.isEqual(today))
            valid = true;

        return valid;
    }
}
