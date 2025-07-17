package com.pucpr.portplace.authentication.features.ahp.constraintValidators.criteriaGroupExists;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CriteriaGroupExistsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CriteriaGroupExists {
    String message() default "Criteria group not found with this ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

