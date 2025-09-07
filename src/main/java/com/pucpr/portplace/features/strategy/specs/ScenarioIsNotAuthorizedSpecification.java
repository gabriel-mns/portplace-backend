package com.pucpr.portplace.features.strategy.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.strategy.entities.Scenario;
import com.pucpr.portplace.features.strategy.enums.ScenarioStatusEnum;
import com.pucpr.portplace.features.strategy.services.internal.ScenarioEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScenarioIsNotAuthorizedSpecification implements Specification<Long> {

    private ScenarioEntityService service;

    @Override
    public boolean isSatisfiedBy(Long scenarioId) {
        
        Scenario scenario = service.getScenarioById(scenarioId);

        return scenario.getStatus() != null && !scenario.getStatus().equals(ScenarioStatusEnum.AUTHORIZED);

    }

}
