package com.pucpr.portplace.core.validation.constraints.dateTimeRange;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateTimeRangeValidator implements ConstraintValidator<ValidDateTimeRange, Object> {


    private String startDateField;
    private String endDateField;

    @Override
    public void initialize(ValidDateTimeRange constraintAnnotation) {
        this.startDateField = constraintAnnotation.startField();
        this.endDateField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            
            var startDateDescriptor = BeanUtils.getPropertyDescriptor(value.getClass(), startDateField);
            var endDateDescriptor = BeanUtils.getPropertyDescriptor(value.getClass(), endDateField);

            if (startDateDescriptor == null || endDateDescriptor == null) {
                return true; // Ignore validation if property descriptors are not found
            }

            var startDate = (LocalDateTime) startDateDescriptor.getReadMethod().invoke(value);
            var endDate = (LocalDateTime) endDateDescriptor.getReadMethod().invoke(value);
            
            if (startDate == null || endDate == null) {
                return true;
            }

            if (endDate.isBefore(startDate)) {
                context.disableDefaultConstraintViolation();
                context
                    .buildConstraintViolationWithTemplate("End date must not be before start date")
                    .addPropertyNode(endDateField)
                    .addConstraintViolation();
                return false;
            }

        } catch (Exception e) {
            return true; // ignora se não conseguir acessar as propriedades
        }

        return true;
    }
}
