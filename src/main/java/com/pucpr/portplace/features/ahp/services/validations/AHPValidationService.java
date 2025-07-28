package com.pucpr.portplace.features.ahp.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.AHPCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.AHPUpdateDTO;
import com.pucpr.portplace.features.ahp.exceptions.AHPNotFoundException;
import com.pucpr.portplace.features.ahp.exceptions.CriteriaGroupNotFoundException;
import com.pucpr.portplace.features.ahp.specs.AHPExistsSpecification;
import com.pucpr.portplace.features.ahp.specs.CriteriaGroupExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPValidationService {

    private CriteriaGroupExistsSpecification criteriaGroupExistsSpecification;
    private AHPExistsSpecification ahpExistsSpecification;

    public void validateBeforeCreation(long strategyId, AHPCreateDTO dto) {
        
        // TODO: After implementing Strategy, validate if the strategyId exists
        long criteriaGroupId = dto.getCriteriaGroupId();

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

    public void validateBeforeUpdate(long strategyId, Long ahpId, AHPUpdateDTO dto) {

        // TODO: After implementing Strategy, validate if the strategyId exists

        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }

        // long criteriaGroupId = dto.getCriteriaGroupId();

        // if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
        //     throw new CriteriaGroupNotFoundException(criteriaGroupId);
        // }

    }

    public void validateBeforeDisable(long strategyId, Long ahpId) {
        
        // TODO: After implementing Strategy, validate if the strategyId exists

        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }

    }

    public void validateBeforeDelete(long strategyId, Long ahpId) {
        // TODO: After implementing Strategy, validate if the strategyId exists

        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }
        
    }

}
