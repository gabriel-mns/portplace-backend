package com.pucpr.portplace.features.ahp.services.validations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.core.validation.specs.EntityIsEnabledSpecification;
import com.pucpr.portplace.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.ahp.exceptions.CriteriaGroupNotFoundException;
import com.pucpr.portplace.features.ahp.exceptions.CriterionNotFoundException;
import com.pucpr.portplace.features.ahp.exceptions.StrategicObjectiveFromDifferentStrategyException;
import com.pucpr.portplace.features.ahp.services.internal.CriteriaGroupEntityService;
import com.pucpr.portplace.features.ahp.services.internal.CriterionEntityService;
import com.pucpr.portplace.features.ahp.specs.CriteriaGroupExistsSpecification;
import com.pucpr.portplace.features.ahp.specs.CriterionExistsSpecification;
import com.pucpr.portplace.features.ahp.specs.StrategicObjectiveFromSameStrategySpecification;
import com.pucpr.portplace.features.strategy.entities.StrategicObjective;
import com.pucpr.portplace.features.strategy.exceptions.StrategicObjectiveNotFoundException;
import com.pucpr.portplace.features.strategy.services.internal.StrategicObjectiveEntityService;
import com.pucpr.portplace.features.strategy.specs.StrategicObjectiveExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriterionValidationService {
    
    private CriteriaGroupEntityService service;
    private CriterionEntityService criterionService;
    private StrategicObjectiveEntityService objectiveService;

    // Specifications
    private EntityIsEnabledSpecification entityIsEnabledSpecification;
    private CriteriaGroupExistsSpecification criteriaGroupExistsSpecification;
    private CriterionExistsSpecification criterionExistsSpecification;
    private StrategicObjectiveExistsSpecification strategicObjectiveExistsSpecification;
    private StrategicObjectiveFromSameStrategySpecification strategicObjectiveFromSameStrategySpecification;

    public void validateBeforeCreation(Long criteriaGroupId) {

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

        CriteriaGroup criteriaGroup = service.getById(criteriaGroupId);

        if(!entityIsEnabledSpecification.isSatisfiedBy(criteriaGroup)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

    public void validateBeforeUpdate(
        Long criterionId, 
        Long criteriaGroupId,
        CriterionUpdateDTO dto
    ) {

        validateBeforeCreation(criteriaGroupId);

        if(!criterionExistsSpecification.isSatisfiedBy(criterionId)) {
            throw new CriterionNotFoundException(criterionId);
        }

        Criterion criterion = criterionService.findById(criterionId);
        List<Long> strategicObjectivesIds = dto.getStrategicObjectives();

        if(strategicObjectivesIds == null || strategicObjectivesIds.isEmpty()) return;

        for (Long id : strategicObjectivesIds) {
            
            if(!strategicObjectiveExistsSpecification.isSatisfiedBy(id)){
                throw new StrategicObjectiveNotFoundException(id);
            }

            StrategicObjective so = objectiveService.findByID(id);
            Long strategyId = criterion.getCriteriaGroup().getStrategy().getId();

            if(!strategicObjectiveFromSameStrategySpecification.isSatisfiedBy(so, strategyId)){
                throw new StrategicObjectiveFromDifferentStrategyException(so.getId(), strategyId);
            }

        }

    }

    public void validateBeforeDeletion(Long id) {

        if(!criterionExistsSpecification.isSatisfiedBy(id)) {
            throw new CriterionNotFoundException(id);
        }

    }

    public void validateBeforeGet(Long criteriaGroupId, Long criterionId) {

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

        if(!criterionExistsSpecification.isSatisfiedBy(criterionId)) {
            throw new CriterionNotFoundException(criterionId);
        }

    }

    public void validateBeforeGetAll(
        Long criteriaGroupId
    ) {

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

        CriteriaGroup criteriaGroup = service.getById(criteriaGroupId);

        if(!entityIsEnabledSpecification.isSatisfiedBy(criteriaGroup)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

}
