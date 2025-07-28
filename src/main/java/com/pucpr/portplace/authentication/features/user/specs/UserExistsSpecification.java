package com.pucpr.portplace.authentication.features.user.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.validation.specs.Specification;
import com.pucpr.portplace.authentication.features.user.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserExistsSpecification implements Specification<Long> {

    private UserRepository userRepository;

    @Override
    public boolean isSatisfiedBy(Long userId) {
        return userRepository.existsById(userId);
    }
    
}
