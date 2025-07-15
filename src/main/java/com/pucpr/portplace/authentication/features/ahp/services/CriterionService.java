package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.mappers.CriterionMapper;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriterionRepository;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriteriaGroupEntityService;
import com.pucpr.portplace.authentication.features.ahp.specs.AllCriteriaComparedSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriterionService {

    private CriteriaGroupEntityService criteriaGroupEntityService;
    private CriterionRepository criterionRepository;
    private AHPResultsService ahpResultsService;
    private CriterionMapper criterionMapper;
    
    // SPECS
    private AllCriteriaComparedSpecification allCriteriaComparedSpecification;

    // CREATE
    public CriterionReadDTO createCriterion(long strategyId, long criteriaGroupId, CriterionCreateDTO criterionCreateDTO) {
        
        Criterion newCriterion = criterionMapper.toEntity(criterionCreateDTO);

        criterionRepository.save(newCriterion);
        
        CriterionReadDTO criterionReadDto = criterionMapper.toReadDTO(newCriterion);

        return criterionReadDto;

    }

    // UPDATE
    public CriterionReadDTO updateCriterion(Long id, CriterionUpdateDTO criterionCreateDTO) {

        //TODO: Treat case when criterion is not found
        Criterion criterion = criterionRepository.findById(id).get();

        criterionMapper.updateFromDTO(criterionCreateDTO, criterion);

        criterionRepository.save(criterion);

        return criterionMapper.toReadDTO(criterion);

    }

    // DELETE
    public void disableCriterion(Long criteriaGroupId, Long id) {

        //TODO: Treat case when criterion is not found
        Criterion criterion = criterionRepository.findById(id).get();

        criterion.setDisabled(true);

        criterionRepository.save(criterion);

    }

    public void deleteCriterion(Long criteriaGroupId, Long id) {

        //TODO: Treat case when criterion is not found
        criterionRepository.deleteById(id);

    }

    // READ
    public CriterionReadDTO getCriterionById(Long criteriaGroupId,Long criterionId) {

        Criterion criterion = criterionRepository.findById(criterionId).get();
        CriterionReadDTO criterionDTO = criterionMapper.toReadDTO(criterion);
        CriteriaGroup criteriaGroup = criteriaGroupEntityService.getById(1, criteriaGroupId);
        boolean allCriteriaCompared = allCriteriaComparedSpecification.isSatisfiedBy(
            criteriaGroup
        );
        List<CriteriaComparison> comparisons = criteriaGroupEntityService
        .getById(1, criteriaGroupId)
        .getCriteriaComparisons();

        if (allCriteriaCompared) {
            double weight = ahpResultsService.getCriterionWeight(criterionId, comparisons);
            criterionDTO.setWeight(weight);
        }

        return criterionDTO;

    }

    public List<CriterionReadDTO> getCriteriaByCriteriaGroupId(long criteriaGroupId, boolean includeDisabled) {

        List<Criterion> criteria;
        CriteriaGroup criteriaGroup = criteriaGroupEntityService.getById(1, criteriaGroupId);
        List<CriteriaComparison> criteriaComparisons = criteriaGroup.getCriteriaComparisons();
        boolean allCriteriaCompared = allCriteriaComparedSpecification.isSatisfiedBy(
            criteriaGroup
        );
        
        boolean includeWeight;
        if(includeDisabled) {

            criteria = criterionRepository.findByCriteriaGroupId(criteriaGroupId);
            includeWeight = false; // If we are including disabled criteria, we cannot include weight as it is not calculated for them.
            
        } else {
            
            criteria = criterionRepository.findByCriteriaGroupIdAndDisabledFalse(criteriaGroupId);
            includeWeight = allCriteriaCompared;

        }

        List<CriterionReadDTO> dtos = criteria.stream()
            .map(criterionMapper::toReadDTO)
            .peek(dto -> {
                if (includeWeight) {
                    double weight = ahpResultsService.getCriterionWeight(dto.getId(), criteriaComparisons);
                    dto.setWeight(weight);
                }
            })
            .toList();

        return dtos;

    }

}
