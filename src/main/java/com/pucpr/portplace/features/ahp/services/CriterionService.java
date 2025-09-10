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
import com.pucpr.portplace.features.strategy.entities.StrategicObjective;
import com.pucpr.portplace.features.strategy.services.internal.StrategicObjectiveEntityService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriterionService {

    private CriteriaGroupEntityService criteriaGroupEntityService;
    private CriterionRepository criterionRepository;
    private AHPCalculationService ahpCalculationService;
    private CriterionMapper criterionMapper;
    private StrategicObjectiveEntityService objectiveService;

    // VALIDATIONS
    private CriterionValidationService validationService;
    
    // SPECS
    private AllCriteriaComparedSpecification allCriteriaComparedSpecification;

    // CREATE
    public CriterionReadDTO createCriterion(long strategyId, long criteriaGroupId, CriterionCreateDTO criterionCreateDTO) {
        
        validationService.validateBeforeCreation(criteriaGroupId);

        Criterion newCriterion = criterionMapper.toEntity(criterionCreateDTO);
        newCriterion.setCriteriaGroup(
            criteriaGroupEntityService.getById(criteriaGroupId)
        );

        criterionRepository.save(newCriterion);

        return criterionMapper.toReadDTO(newCriterion);

    }

    // UPDATE
    @Transactional
    public CriterionReadDTO updateCriterion(
        Long criterionId, 
        Long criteriaGroupId,
        CriterionUpdateDTO criterionUpdateDTO
    ) {

        validationService.validateBeforeUpdate(criterionId, criteriaGroupId, criterionUpdateDTO);

        Criterion criterion = criterionRepository.findById(criterionId).get();

        criterionMapper.updateFromDTO(criterionUpdateDTO, criterion);

        if (criterionUpdateDTO.getStrategicObjectives() != null) {
            // Limpa os relacionamentos atuais
            criterion.getStrategicObjectives().clear();

            // Busca os novos StrategicObjectives e adiciona
            List<StrategicObjective> newObjectives = objectiveService
                .findAllById(criterionUpdateDTO.getStrategicObjectives());

            criterion.getStrategicObjectives().addAll(newObjectives);
        }

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
        CriteriaGroup criteriaGroup = criteriaGroupEntityService.getById(criteriaGroupId);
        boolean allCriteriaCompared = allCriteriaComparedSpecification.isSatisfiedBy(
            criteriaGroup
        );
        List<CriteriaComparison> comparisons = criteriaGroupEntityService
            .getById(criteriaGroupId)
            .getCriteriaComparisons();

        if (allCriteriaCompared) {
            double weight = ahpCalculationService.getCriterionWeight(criterionId, comparisons);
            criterionDTO.setWeight(weight);
        }

        return criterionDTO;

    }

    public Page<CriterionReadDTO> getCriteriaByFilters(
        Long criteriaGroupId,
        boolean includeDisabled,
        String name,
        Pageable pageable
        ) {

        validationService.validateBeforeGetAll(criteriaGroupId);

        Page<Criterion> criteria = criterionRepository.findByCriteriaGroupId(
            criteriaGroupId,
            includeDisabled,
            name,
            pageable
        );

        
        List<CriterionReadDTO> dtos = criteria.getContent().stream()
            .map(criterionMapper::toReadDTO)
            .peek(criterion -> {

                // Get CriteriaGroup from that criterion
                CriteriaGroup criteriaGroup = criteriaGroupEntityService.getById(criteriaGroupId);
                // Get all active comparisons from that CriteriaGroup
                List<CriteriaComparison> criteriaComparisons = criteriaGroup.getCriteriaComparisons()
                                                                .stream()
                                                                .filter(cc -> cc.isDisabled() == false)
                                                                .toList();

                // Check if all criteria are compared
                boolean allCriteriaCompared = allCriteriaComparedSpecification.isSatisfiedBy(
                    criteriaGroup
                    );

                // If the criterion it`s disabled, it does not count and does not receive weight
                boolean includeWeight = 
                    !criteriaGroup.isDisabled() && 
                    !criterion.isDisabled() &&
                    criteriaComparisons.size() > 0 &&
                    allCriteriaCompared;

                // If it`s possible to include weight
                if (includeWeight) {
                    double weight = ahpCalculationService.getCriterionWeight(criterion.getId(), criteriaComparisons);
                    criterion.setWeight(weight);
                }

            })
            .toList();

        return new PageImpl<>(dtos, pageable, criteria.getTotalElements());

    }

}
