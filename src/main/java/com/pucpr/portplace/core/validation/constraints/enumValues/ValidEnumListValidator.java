package com.pucpr.portplace.core.validation.constraints.enumValues;



import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEnumListValidator implements ConstraintValidator<ValidEnum, List<String>> {

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
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) return true;

        for (String value : values) {
            if (!acceptedValues.contains(value)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                    messageTemplate.replace("{acceptedValues}", acceptedValues.toString())
                ).addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}