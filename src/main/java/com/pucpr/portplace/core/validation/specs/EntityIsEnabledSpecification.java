package com.pucpr.portplace.core.validation.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.entities.AuditableEntity;

@Component
public class EntityIsEnabledSpecification implements Specification<AuditableEntity> {

    @Override
    public boolean isSatisfiedBy(AuditableEntity entity) {
        return entity != null && !entity.isDisabled();
    }
    
}
