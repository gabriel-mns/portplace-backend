package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.mappers.CriteriaComparisonMapper;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriteriaComparisonRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriteriaComparisonService {
    
    private CriteriaComparisonRepository criteriaComparisonRepository;
    private CriteriaComparisonMapper criteriaComparisonMapper;

    // CREATE
    public CriteriaComparisonReadDTO createCriteriaComparison(long strategyId, long criteriaGroupId, CriteriaComparisonCreateDTO criteriaComparisonCreateDTO) {
        
        //TODO: Treat case when comparedCriterion and referenceCriterion are not found
        //TODO: Treat case when ahp is not found
        //TODO: Treat case when comparedCriterion and referenceCriterion are the same
        //TODO: Treat case when comparedCriterion and referenceCriterion are already compared
        //TODO: Treat case when comparedCriterion and referenceCriterion are already compared in the opposite direction
        //TODO: Treat case when comparedCriterion and referenceCriterion are already compared in the opposite direction with different importanceScale
        //TODO: Treat case when comparedCriterion or referenceCriterion are not in the same AHP
        
        CriteriaComparison newCriteriaComparison = criteriaComparisonMapper.toEntity(criteriaComparisonCreateDTO);

        criteriaComparisonRepository.save(newCriteriaComparison);

        return criteriaComparisonMapper.toReadDTO(newCriteriaComparison);
        
    }

    // UPDATE
    public CriteriaComparisonReadDTO updateCriteriaComparison(long criteriaComparisonId, CriteriaComparisonUpdateDTO criteriaComparisonCreateDTO) {
        
        //TODO: Treat case when criteriaComparison is disabled
        //TODO: Treat case when criteriaComparison was not found

        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();

        criteriaComparisonMapper.updateFromDTO(criteriaComparisonCreateDTO, criteriaComparison);
        
        criteriaComparisonRepository.save(criteriaComparison);
        
        return criteriaComparisonMapper.toReadDTO(criteriaComparison);
    
    }

    // DELETE
    public void disableCriteriaComparison(long criteriaComparisonId) {

        //TODO: Treat case when criteriaComparison is already disabled
        //TODO: Treat case when criteriaComparison was not found
        
        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();

        criteriaComparison.setDisabled(true);
        
        criteriaComparisonRepository.save(criteriaComparison);
    
    }

    public void deleteCriteriaComparison(long criteriaComparisonId) {
        
        //TODO: Treat case when criteriaComparison was not found

        criteriaComparisonRepository.deleteById(criteriaComparisonId);
    
    }

    // READ
    public CriteriaComparisonReadDTO getCriteriaComparisonById(long criteriaComparisonId) {
        
        //TODO: Treat case when criteriaComparison was not found
        
        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();
        
        CriteriaComparisonReadDTO criteriaComparisonDTO = criteriaComparisonMapper.toReadDTO(criteriaComparison);

        return criteriaComparisonDTO;

    }


    public List<CriteriaComparisonReadDTO> getCriteriaComparisons(long strategyId, long criteriaGroupId, Long comparedCriterionId, Long referenceCriterionId, boolean includeDisabled) {

        boolean hasComparedCriterion = comparedCriterionId != null;
        boolean hasReferenceCriterion = referenceCriterionId != null;

        if (hasComparedCriterion && hasReferenceCriterion) {
            return getCriteriaComparisonByComparedCriterionIdAndReferenceCriterionIdAndAhpId(comparedCriterionId, referenceCriterionId, criteriaGroupId, includeDisabled);
        } else if (hasComparedCriterion) {
            return getCriteriaComparisonByComparedCriterionIdAndAhpId(comparedCriterionId, criteriaGroupId, includeDisabled);
        } else if (hasReferenceCriterion) {
            return getCriteriaComparisonByReferenceCriterionIdAndAhpId(referenceCriterionId, criteriaGroupId, includeDisabled);
        } else {
            return getCriteriaComparisonByAhpId(criteriaGroupId, includeDisabled);
        }
    
    }

    public List<CriteriaComparisonReadDTO> getCriteriaComparisonByComparedCriterionIdAndAhpId(long comparedCriterionId, long criteriaGroupId, boolean includeDisabled) {

        List<CriteriaComparison> criteriaComparisonList;

        if(includeDisabled){

            criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndCriteriaGroupId(comparedCriterionId, criteriaGroupId);

        } else {

            criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndCriteriaGroupIdAndDisabledFalse(comparedCriterionId, criteriaGroupId);

        }

        List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonMapper.toReadDTO(criteriaComparisonList);

        return criteriaComparisonDTOList;
    
    }

    public List<CriteriaComparisonReadDTO> getCriteriaComparisonByReferenceCriterionIdAndAhpId(long referenceCriterionId, long criteriaGroupId, boolean includeDisabled) {

        List<CriteriaComparison> criteriaComparisonList;

        if(includeDisabled){

            criteriaComparisonList = criteriaComparisonRepository.findByReferenceCriterionIdAndCriteriaGroupId(referenceCriterionId, criteriaGroupId);

        } else {

            criteriaComparisonList = criteriaComparisonRepository.findByReferenceCriterionIdAndCriteriaGroupIdAndDisabledFalse(referenceCriterionId, criteriaGroupId);

        }

        List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonMapper.toReadDTO(criteriaComparisonList);

        return criteriaComparisonDTOList;
    
    }

    public List<CriteriaComparisonReadDTO> getCriteriaComparisonByComparedCriterionIdAndReferenceCriterionIdAndAhpId(long comparedCriterionId, long referenceCriterionId, long criteriaGroupId, boolean includeDisabled) {

        List<CriteriaComparison> criteriaComparisonList;

        if(includeDisabled){

            criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndReferenceCriterionIdAndCriteriaGroupId(comparedCriterionId, referenceCriterionId, criteriaGroupId);

        } else {

            criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndReferenceCriterionIdAndCriteriaGroupIdAndDisabledFalse(comparedCriterionId, referenceCriterionId, criteriaGroupId);

        }

        List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonMapper.toReadDTO(criteriaComparisonList);

        return criteriaComparisonDTOList;
    
    }

    public List<CriteriaComparisonReadDTO> getCriteriaComparisonByAhpId(long criteriaGroupId, boolean includeDisabled) {

        List<CriteriaComparison> criteriaComparisonList;

        if(includeDisabled){

            criteriaComparisonList = criteriaComparisonRepository.findByCriteriaGroupId(criteriaGroupId);

        } else {

            criteriaComparisonList = criteriaComparisonRepository.findByCriteriaGroupIdAndDisabledFalse(criteriaGroupId);

        }

        List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonMapper.toReadDTO(criteriaComparisonList);

        return criteriaComparisonDTOList;
    
    }

}
