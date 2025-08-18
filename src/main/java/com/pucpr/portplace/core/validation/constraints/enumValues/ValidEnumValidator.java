package com.pucpr.portplace.core.validation.constraints.enumValues;



import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Set<String> acceptedValues;
    private String messageTemplate;

    @Override
    public void initialize(ValidEnum annotation) {
        acceptedValues = Arrays.stream(annotation.enumClass().getEnumConstants())
            .map(Enum::name)
            .collect(Collectors.toSet());
        messageTemplate = annotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (acceptedValues.contains(value)) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
            messageTemplate.replace("{acceptedValues}", acceptedValues.toString())
        ).addConstraintViolation();

        return false;
    }
}