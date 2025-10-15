package com.pucpr.portplace.features.user.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.user.entities.User;
import com.pucpr.portplace.features.user.enums.UserStatusEnum;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserIsActiveSpecification implements Specification<User> {

    @Override
    public boolean isSatisfiedBy(User user) {
        return user != null && user.getStatus().equals(UserStatusEnum.ACTIVE);
    }
    
}
