package com.pucpr.portplace.authentication.core.validation.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.entities.AuditableEntity;

@Component
public class EntityIsEnabledSpecification implements Specification<AuditableEntity> {

    @Override
    public boolean isSatisfiedBy(AuditableEntity entity) {
        return entity != null && !entity.isDisabled();
    }
    
}
