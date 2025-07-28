package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.mappers.CriteriaComparisonMapper;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriteriaComparisonRepository;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriteriaComparisonEntityService;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriterionEntityService;
import com.pucpr.portplace.authentication.features.ahp.services.validations.CriteriaComparisonValidationService;
import com.pucpr.portplace.authentication.features.ahp.specs.CriteriaNotComparedSpecification;

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
        
        //TODO: Treat case when comparedCriterion or referenceCriterion are not in the same AHP

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
        
        //TODO: Treat case when criteriaComparison is disabled
        //TODO: Treat case when criteriaComparison was not found

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

    public List<CriteriaComparisonReadDTO> getCriteriaComparisons(long strategyId, long criteriaGroupId, Long criterion1Id, Long criterion2Id, boolean includeDisabled) {

        validationService.validateBeforeGetAll(criteriaGroupId);
        
        List<CriteriaComparison> criteriaComparisons = criteriaComparisonRepository.findComparisons(
            criteriaGroupId, criterion2Id, criterion1Id, includeDisabled
        );

        return criteriaComparisonMapper.toReadDTO(criteriaComparisons);

        // if (hasComparedCriterion && hasReferenceCriterion) {
        //     return getCriteriaComparisonByComparedCriterionIdAndReferenceCriterionIdAndAhpId(comparedCriterionId, referenceCriterionId, criteriaGroupId, includeDisabled);
        // } else if (hasComparedCriterion) {
        //     return getCriteriaComparisonByComparedCriterionIdAndAhpId(comparedCriterionId, criteriaGroupId, includeDisabled);
        // } else if (hasReferenceCriterion) {
        //     return getCriteriaComparisonByReferenceCriterionIdAndAhpId(referenceCriterionId, criteriaGroupId, includeDisabled);
        // } else {
        //     return getCriteriaComparisonByAhpId(criteriaGroupId, includeDisabled);
        // }
    
    }

    // public List<CriteriaComparisonReadDTO> getCriteriaComparisonByComparedCriterionIdAndAhpId(long comparedCriterionId, long criteriaGroupId, boolean includeDisabled) {

    //     List<CriteriaComparison> criteriaComparisonList;

    //     if(includeDisabled){

    //         criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndCriteriaGroupId(comparedCriterionId, criteriaGroupId);

    //     } else {

    //         criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndCriteriaGroupIdAndDisabledFalse(comparedCriterionId, criteriaGroupId);

    //     }

    //     List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonMapper.toReadDTO(criteriaComparisonList);

    //     return criteriaComparisonDTOList;
    
    // }

    // public List<CriteriaComparisonReadDTO> getCriteriaComparisonByReferenceCriterionIdAndAhpId(long referenceCriterionId, long criteriaGroupId, boolean includeDisabled) {

    //     List<CriteriaComparison> criteriaComparisonList;

    //     if(includeDisabled){

    //         criteriaComparisonList = criteriaComparisonRepository.findByReferenceCriterionIdAndCriteriaGroupId(referenceCriterionId, criteriaGroupId);

    //     } else {

    //         criteriaComparisonList = criteriaComparisonRepository.findByReferenceCriterionIdAndCriteriaGroupIdAndDisabledFalse(referenceCriterionId, criteriaGroupId);

    //     }

    //     List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonMapper.toReadDTO(criteriaComparisonList);

    //     return criteriaComparisonDTOList;
    
    // }

    // public List<CriteriaComparisonReadDTO> getCriteriaComparisonByComparedCriterionIdAndReferenceCriterionIdAndAhpId(long comparedCriterionId, long referenceCriterionId, long criteriaGroupId, boolean includeDisabled) {

    //     List<CriteriaComparison> criteriaComparisonList;

    //     if(includeDisabled){

    //         criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndReferenceCriterionIdAndCriteriaGroupId(comparedCriterionId, referenceCriterionId, criteriaGroupId);

    //     } else {

    //         criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndReferenceCriterionIdAndCriteriaGroupIdAndDisabledFalse(comparedCriterionId, referenceCriterionId, criteriaGroupId);

    //     }

    //     List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonMapper.toReadDTO(criteriaComparisonList);

    //     return criteriaComparisonDTOList;
    
    // }

    // public List<CriteriaComparisonReadDTO> getCriteriaComparisonByAhpId(long criteriaGroupId, boolean includeDisabled) {

    //     List<CriteriaComparison> criteriaComparisonList;

    //     if(includeDisabled){

    //         criteriaComparisonList = criteriaComparisonRepository.findByCriteriaGroupId(criteriaGroupId);

    //     } else {

    //         criteriaComparisonList = criteriaComparisonRepository.findByCriteriaGroupIdAndDisabledFalse(criteriaGroupId);

    //     }

    //     List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonMapper.toReadDTO(criteriaComparisonList);

    //     return criteriaComparisonDTOList;
    
    // }

}
