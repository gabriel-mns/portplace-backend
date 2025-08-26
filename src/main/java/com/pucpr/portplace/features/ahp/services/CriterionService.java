package com.pucpr.portplace.features.ahp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.ahp.mappers.CriterionMapper;
import com.pucpr.portplace.features.ahp.repositories.CriterionRepository;
import com.pucpr.portplace.features.ahp.services.internal.AHPCalculationService;
import com.pucpr.portplace.features.ahp.services.internal.CriteriaGroupEntityService;
import com.pucpr.portplace.features.ahp.services.validations.CriterionValidationService;
import com.pucpr.portplace.features.ahp.specs.AllCriteriaComparedSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriterionService {

    private CriteriaGroupEntityService criteriaGroupEntityService;
    private CriterionRepository criterionRepository;
    private AHPCalculationService ahpCalculationService;
    private CriterionMapper criterionMapper;

    // VALIDATIONS
    private CriterionValidationService validationService;
    
    // SPECS
    private AllCriteriaComparedSpecification allCriteriaComparedSpecification;

    // CREATE
    public CriterionReadDTO createCriterion(long strategyId, long criteriaGroupId, CriterionCreateDTO criterionCreateDTO) {
        
        validationService.validateBeforeCreation(criteriaGroupId);

        Criterion newCriterion = criterionMapper.toEntity(criterionCreateDTO);
        newCriterion.setCriteriaGroup(
            criteriaGroupEntityService.getById(strategyId, criteriaGroupId)
        );

        criterionRepository.save(newCriterion);

        return criterionMapper.toReadDTO(newCriterion);

    }

    // UPDATE
    public CriterionReadDTO updateCriterion(Long criterionId, Long criteriaGroupId, CriterionUpdateDTO criterionUpdateDTO) {

        validationService.validateBeforeUpdate(criterionId, criteriaGroupId);

        Criterion criterion = criterionRepository.findById(criterionId).get();

        criterionMapper.updateFromDTO(criterionUpdateDTO, criterion);

        criterionRepository.save(criterion);

        return criterionMapper.toReadDTO(criterion);

    }

    // DELETE
    public void disableCriterion(Long criteriaGroupId, Long id) {

        validationService.validateBeforeDeletion(id);

        Criterion criterion = criterionRepository.findById(id).get();

        criterion.setDisabled(true);

        criterionRepository.save(criterion);

    }

    public void deleteCriterion(Long criteriaGroupId, Long id) {

        validationService.validateBeforeDeletion(id);

        criterionRepository.deleteById(id);

    }

    // READ
    public CriterionReadDTO getCriterionById(Long criteriaGroupId,Long criterionId) {

        validationService.validateBeforeGet(criteriaGroupId, criterionId);

        Criterion criterion = criterionRepository.findById(criterionId).get();
        CriterionReadDTO criterionDTO = criterionMapper.toReadDTO(criterion);
        
        // Weight calculation
        CriteriaGroup criteriaGroup = criteriaGroupEntityService.getById(1, criteriaGroupId);
        boolean allCriteriaCompared = allCriteriaComparedSpecification.isSatisfiedBy(
            criteriaGroup
        );
        List<CriteriaComparison> comparisons = criteriaGroupEntityService
            .getById(1, criteriaGroupId)
            .getCriteriaComparisons();

        if (allCriteriaCompared) {
            double weight = ahpCalculationService.getCriterionWeight(criterionId, comparisons);
            criterionDTO.setWeight(weight);
        }

        return criterionDTO;

    }

    public Page<CriterionReadDTO> getCriteriaByCriteriaGroupId(
        long criteriaGroupId, 
        boolean includeDisabled,
        String name,
        Pageable pageable
        ) {

        validationService.validateBeforeGetAll(criteriaGroupId);

        CriteriaGroup criteriaGroup = criteriaGroupEntityService.getById(1, criteriaGroupId);
        List<CriteriaComparison> criteriaComparisons = criteriaGroup.getCriteriaComparisons();
        boolean allCriteriaCompared = allCriteriaComparedSpecification.isSatisfiedBy(
            criteriaGroup
            );
        boolean includeWeight = !includeDisabled && allCriteriaCompared;
        boolean containsName = name != null && !name.isEmpty();
        Page<Criterion> criteria;

        if(includeDisabled) {
            if(containsName){
                criteria = criterionRepository.findByCriteriaGroupIdAndNameContainingIgnoreCase(criteriaGroupId, name, pageable);
            } else {
                criteria = criterionRepository.findByCriteriaGroupId(criteriaGroupId, pageable);
            }

        } else {
            if(containsName){
                criteria = criterionRepository.findByCriteriaGroupIdAndDisabledFalseAndNameContainingIgnoreCase(criteriaGroupId, name, pageable);
            } else {
                criteria = criterionRepository.findByCriteriaGroupIdAndDisabledFalse(criteriaGroupId, pageable);
            }
        }

        List<CriterionReadDTO> dtos = criteria.getContent().stream()
            .map(criterionMapper::toReadDTO)
            .peek(dto -> {
                if (includeWeight) {
                    double weight = ahpCalculationService.getCriterionWeight(dto.getId(), criteriaComparisons);
                    dto.setWeight(weight);
                }
            })
            .toList();

        return new PageImpl<>(dtos, pageable, criteria.getTotalElements());

    }

}
