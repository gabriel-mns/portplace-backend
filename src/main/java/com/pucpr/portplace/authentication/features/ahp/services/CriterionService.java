package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriterionRepository;

@Service
public class CriterionService {

    @Autowired
    private CriteriaGroupService criteriaGroupService;

    @Autowired
    private CriterionRepository criterionRepository;

    @Autowired
    private AHPResultsService ahpResultsService;

    // CREATE
    public ResponseEntity<Void> createCriterion(long strategyId, long criteriaGroupId, CriterionCreateDTO criterionCreateDTO) {
        
        Criterion criterion = new Criterion();

        CriteriaGroup criteriaGroup = criteriaGroupService.getCriteriaGroupEntityById(strategyId, criteriaGroupId);

        criterion.setName(criterionCreateDTO.getName());
        criterion.setDescription(criterionCreateDTO.getDescription());
        criterion.setCriteriaGroup(criteriaGroup);

        criterionRepository.save(criterion);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    // UPDATE
    public ResponseEntity<Void> updateCriterion(Long id, CriterionUpdateDTO criterionCreateDTO) {

        //TODO: Treat case when criterion is not found
        Criterion criterion = criterionRepository.findById(id).get();

        criterion.setName(criterionCreateDTO.getName());
        criterion.setDescription(criterionCreateDTO.getDescription());

        criterionRepository.save(criterion);

        return ResponseEntity.ok().build();

    }

    // DELETE
    public ResponseEntity<Void> disableCriterion(Long criteriaGroupId, Long id) {

        //TODO: Treat case when criterion is not found
        Criterion criterion = criterionRepository.findById(id).get();

        criterion.setDisabled(true);

        criterionRepository.save(criterion);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    public ResponseEntity<Void> deleteCriterion(Long criteriaGroupId, Long id) {

        //TODO: Treat case when criterion is not found
        criterionRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    // READ
    public ResponseEntity<CriterionReadDTO> getCriterionById(Long criteriaGroupId,Long id) {

        Criterion criterion = criterionRepository.findById(id).get();
        CriterionReadDTO criterionReadDto = new CriterionReadDTO();

        criterionReadDto.setId(criterion.getId());
        criterionReadDto.setName(criterion.getName());
        criterionReadDto.setDescription(criterion.getDescription());
        criterionReadDto.setCriteriaGroupId(criterion.getCriteriaGroup().getId());
        criterionReadDto.setLastModifiedAt(criterion.getLastModifiedAt());
        criterionReadDto.setCreatedAt(criterion.getCreatedAt());
        // criterionReadDto.setLastModifiedBy(criterion.getLastModifiedBy());
        criterionReadDto.setDisabled(criterion.isDisabled());

        return ResponseEntity.ok(criterionReadDto);

    }

    public Criterion getCriterionEntityById(Long id) {

        Criterion criterion = criterionRepository.findById(id).get();

        return criterion;

    }

    public ResponseEntity<List<CriterionReadDTO>> getCriteriaByCriteriaGroupId(long criteriaGroupId, boolean includeWeight, boolean includeDisabled) {

        List<Criterion> criteria;

        List<CriteriaComparison> criteriaComparisons = criteriaGroupService.getCriteriaGroupEntityById(1, criteriaGroupId).getCriteriaComparisons();

        if(includeDisabled) {

            criteria = criterionRepository.findByCriteriaGroupId(criteriaGroupId);

        } else {

            criteria = criterionRepository.findByCriteriaGroupIdAndDisabledFalse(criteriaGroupId);

        }

        List<CriterionReadDTO> criteriaDTOs = criteria.stream().map(criterion -> {
            CriterionReadDTO criterionReadDto = new CriterionReadDTO();
            criterionReadDto.setId(criterion.getId());
            criterionReadDto.setName(criterion.getName());
            criterionReadDto.setDescription(criterion.getDescription());
            criterionReadDto.setCriteriaGroupId(criterion.getCriteriaGroup().getId());
            criterionReadDto.setLastModifiedAt(criterion.getLastModifiedAt());
            criterionReadDto.setCreatedAt(criterion.getCreatedAt());
            criterionReadDto.setDisabled(criterion.isDisabled());

            if (includeWeight) {
                criterionReadDto.setWeight(ahpResultsService.getCriterionWeight(criterion.getId(), criteriaComparisons));
            }

            return criterionReadDto;
        }).toList();

        return ResponseEntity.ok(criteriaDTOs);

    }

}
