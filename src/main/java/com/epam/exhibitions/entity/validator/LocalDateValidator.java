package com.epam.exhibitions.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class LocalDateValidator implements ConstraintValidator<LocalDateNotEmpty, LocalDate> {
    @Override
    public void initialize(LocalDateNotEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        Boolean valid = false;
        LocalDate today = LocalDate.now();

        if(null != localDate && localDate.isBefore(today))
            valid = true;
        else if(null != localDate && localDate.isAfter(today))
            valid = true;
        else if (null != localDate && localDate.isEqual(today))
            valid = true;

        return valid;
    }
}
