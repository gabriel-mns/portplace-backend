package com.pucpr.portplace.features.user.constraintValidators;

import com.pucpr.portplace.features.user.services.internal.UserEntityService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserExistsValidator implements ConstraintValidator<UserExists, Long> {

    private UserEntityService userService;

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext context) {

        return userService.existsById(userId);

    }

}
