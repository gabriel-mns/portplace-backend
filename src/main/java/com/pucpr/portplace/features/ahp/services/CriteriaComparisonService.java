package com.pucpr.portplace.features.ahp.services;

import java.util.logging.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.CriteriaComparisonCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaComparisonReadDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaComparisonUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.ahp.mappers.CriteriaComparisonMapper;
import com.pucpr.portplace.features.ahp.repositories.CriteriaComparisonRepository;
import com.pucpr.portplace.features.ahp.services.internal.CriteriaComparisonEntityService;
import com.pucpr.portplace.features.ahp.services.internal.CriterionEntityService;
import com.pucpr.portplace.features.ahp.services.validations.CriteriaComparisonValidationService;
import com.pucpr.portplace.features.ahp.specs.CriteriaNotComparedSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriteriaComparisonService {
    
    private CriterionEntityService criterionEntityService;
    private CriteriaComparisonRepository criteriaComparisonRepository;
    private CriteriaComparisonMapper criteriaComparisonMapper;
    private CriteriaComparisonValidationService validationService;
    private CriteriaComparisonEntityService criteriaComparisonEntityService;
    private CriteriaNotComparedSpecification criteriaNotComparedSpecification;

    private static final Logger logger = Logger.getLogger(CriteriaComparisonService.class.getName());

    // CREATE
    public CriteriaComparisonReadDTO createCriteriaComparison(long strategyId, long criteriaGroupId, CriteriaComparisonCreateDTO criteriaComparisonCreateDTO) {
    
        validationService.validateBeforeCreation(strategyId, criteriaGroupId, criteriaComparisonCreateDTO.getComparedCriterionId(), criteriaComparisonCreateDTO.getReferenceCriterionId());

        Criterion compared = criterionEntityService.findById(criteriaComparisonCreateDTO.getComparedCriterionId());
        Criterion reference = criterionEntityService.findById(criteriaComparisonCreateDTO.getReferenceCriterionId());

        if(!criteriaNotComparedSpecification.isSatisfiedBy(compared, reference)) {

            criteriaComparisonEntityService.disableCriteriaComparison(compared, reference);
            logger.info("Criteria are already compared, previous comparison will be disabled.");

        }

        criteriaComparisonCreateDTO.setCriteriaGroupId(criteriaGroupId);

        CriteriaComparison newCriteriaComparison = criteriaComparisonMapper.toEntity(criteriaComparisonCreateDTO);

        criteriaComparisonRepository.save(newCriteriaComparison);

        return criteriaComparisonMapper.toReadDTO(newCriteriaComparison);
        
    }

    // UPDATE
    public CriteriaComparisonReadDTO updateCriteriaComparison(long strategyId, long criteriaComparisonId, CriteriaComparisonUpdateDTO dto) {

        validationService.validateBeforeUpdate(
            criteriaComparisonId,
            strategyId
        );

        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();

        criteriaComparisonMapper.updateFromDTO(dto, criteriaComparison);
        
        criteriaComparisonRepository.save(criteriaComparison);
        
        return criteriaComparisonMapper.toReadDTO(criteriaComparison);
    
    }

    // DELETE
    public void disableCriteriaComparison(long criteriaComparisonId) {

        validationService.validateBeforeDisable(criteriaComparisonId);
        
        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();

        criteriaComparison.setDisabled(true);
        
        criteriaComparisonRepository.save(criteriaComparison);
    
    }

    public void deleteCriteriaComparison(long criteriaComparisonId) {

        validationService.validateBeforeDelete(criteriaComparisonId);

        criteriaComparisonRepository.deleteById(criteriaComparisonId);
    
    }

    // READ
    public CriteriaComparisonReadDTO getCriteriaComparisonById(long criteriaComparisonId) {
        
        validationService.validateBeforeGet(criteriaComparisonId);
        
        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();
        
        CriteriaComparisonReadDTO criteriaComparisonDTO = criteriaComparisonMapper.toReadDTO(criteriaComparison);

        return criteriaComparisonDTO;

    }

    public Page<CriteriaComparisonReadDTO> getCriteriaComparisons(
        long strategyId,
        long criteriaGroupId,
        Long referenceCriterionId, 
        Long comparedCriterionId, 
        String criterionName,
        boolean includeDisabled,
        Pageable pageable
        ) {

        validationService.validateBeforeGetAll(criteriaGroupId);

        boolean containsName = criterionName != null && !criterionName.isEmpty();
        boolean searchByReference = referenceCriterionId != null;
        boolean searchByCompared = comparedCriterionId != null;
        
        Page<CriteriaComparison> criteriaComparisons;

        
        if(!containsName) criterionName = "";

        if(searchByReference && !searchByCompared) {
            criteriaComparisons = criteriaComparisonRepository.findComparisonsByReferenceCriterion(
                criteriaGroupId, referenceCriterionId, criterionName, includeDisabled, pageable
            );
        } else if(searchByCompared && !searchByReference) {
            criteriaComparisons = criteriaComparisonRepository.findComparisonsByComparedCriterion(
                criteriaGroupId, comparedCriterionId, criterionName, includeDisabled, pageable
            );
        } else {
            criteriaComparisons = criteriaComparisonRepository.findByCriteriaGroupId(criteriaGroupId, pageable);
        }
        
        return criteriaComparisons.map(criteriaComparisonMapper::toReadDTO);
    
    }

    // public Page<CriteriaComparisonReadDTO> getCriteriaComparisons(
    //     long strategyId,
    //     long criteriaGroupId,
    //     Long criterion1Id,
    //     Long criterion2Id,
    //     boolean includeDisabled,
    //     Pageable pageable
    //     ) {

    //     validationService.validateBeforeGetAll(criteriaGroupId);
        
    //     Page<CriteriaComparison> criteriaComparisons = criteriaComparisonRepository.findComparisons(
    //         criteriaGroupId, criterion2Id, criterion1Id, includeDisabled, pageable
    //     );

    //     return criteriaComparisons.map(criteriaComparisonMapper::toReadDTO);
    
    // }

}
