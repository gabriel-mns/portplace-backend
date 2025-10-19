package com.pucpr.portplace.features.user.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;

@Component
public class ValidPasswordSpecification implements Specification<String> {

    private static final int MIN_PASSWORD_LENGTH = 5;

    @Override
    public boolean isSatisfiedBy(String password) {

        return password == null || (hasMinimumLength(password) && isNotBlank(password));
    }

    private boolean hasMinimumLength(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isNotBlank(String password) {
        return !password.trim().isEmpty();
    }
    
}
