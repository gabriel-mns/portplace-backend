package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.user.enums.RoleEnum;
import com.pucpr.portplace.features.user.services.internal.UserEntityService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class OwnerIsPMOSpecification implements Specification<Long> {

    private UserEntityService userService;

    @Override
    public boolean isSatisfiedBy(Long ownerId) {
        
        RoleEnum userRole = userService.getUserByIdEntity(ownerId).getRole();

        return userRole == RoleEnum.PMO || userRole == RoleEnum.PMO_ADM;

    }
    
}
