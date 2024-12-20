package com.example.incidentManagement.validation;

import com.example.incidentManagement.common.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class StatusValidator implements ConstraintValidator<ValidStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && Status.isValid(value);
    }
}
