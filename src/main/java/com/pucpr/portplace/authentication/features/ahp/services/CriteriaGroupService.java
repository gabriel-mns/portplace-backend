package com.pucpr.portplace.authentication.features.ahp.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupListReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;
// import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriteriaGroupRepository;
import com.pucpr.portplace.authentication.features.ahp.repositories.StrategyRepository;

@Service
public class CriteriaGroupService {
    
    @Autowired
    private CriteriaGroupRepository criteriaGroupRepository;

    // @Autowired
    // private CriterionService criterionService;
    @Autowired
    private StrategyRepository strategyRepository;
    

    // CREATE
    public ResponseEntity<Void> createCriteriaGroup(long strategyId, CriteriaGroupCreateDTO criteriaGroupCreateDto) {

        // List<Long> criteriaIdList = criteriaGroupCreateDto.getCriteriaIdList();
        
        CriteriaGroup criteriaGroup = CriteriaGroup.builder()
        .name(criteriaGroupCreateDto.getName())
        .description(criteriaGroupCreateDto.getDescription())
        .strategy(strategyRepository.findById(strategyId).get())
        .build();

        // if( criteriaIdList != null && !criteriaIdList.isEmpty() ) {
            
        //     List<Criterion> criteriaList = criteriaIdList.stream()
        //         .map(id -> criterionService.getCriterionEntityById(id))
        //         .toList();

        //     criteriaGroup.setCriteria(criteriaList);

        // }

        criteriaGroupRepository.save(criteriaGroup);

        return ResponseEntity.status(Response.SC_CREATED).build();

    }

    // UPDATE
    public ResponseEntity<Void> updateCriteriaGroup(long strategyId, long criteriaGroupId, CriteriaGroupUpdateDTO criteriaGroupCreateDto) {

        //TODO: Treat not found exception
        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        // List<Long> criteriaIdList = criteriaGroupCreateDto.getCriteriaIdList();

        // if( criteriaIdList != null && !criteriaIdList.isEmpty() ) {
            
        //     List<Criterion> criteriaList = criteriaIdList.stream()
        //         .map(criterionService::getCriterionEntityById)
        //         .toList();

        //     criteriaGroup.setCriteria(criteriaList);

        // }

        criteriaGroup.setName(criteriaGroupCreateDto.getName());
        criteriaGroup.setDescription(criteriaGroupCreateDto.getDescription());
        criteriaGroup.setLastModifiedAt(LocalDateTime.now());

        criteriaGroupRepository.save(criteriaGroup);

        return ResponseEntity.ok().build();

    }

    // DELETE
    public ResponseEntity<Void> disableCriteriaGroup(long strategyId, long criteriaGroupId) {

        //TODO: Treat not found exception
        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        criteriaGroup.setDisabled(true);

        criteriaGroupRepository.save(criteriaGroup);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    public ResponseEntity<Void> deleteCriteriaGroup(long strategyId, long criteriaGroupId) {

        //TODO: Treat not found exception
        criteriaGroupRepository.deleteById(criteriaGroupId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    // READ
    public ResponseEntity<CriteriaGroupReadDTO> getCriteriaGroupById(long strategyId, long criteriaGroupId) {

        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        // Convert CriteriaGroup entity to DTO
        List<CriterionReadDTO> criteriaDTOs = criteriaGroup.getCriteria().stream().map(criterion -> {
           
            CriterionReadDTO criterionReadDto = new CriterionReadDTO();
            criterionReadDto.setId(criterion.getId());
            criterionReadDto.setName(criterion.getName());
            criterionReadDto.setDescription(criterion.getDescription());
            criterionReadDto.setCreatedAt(criterion.getCreatedAt());
            criterionReadDto.setDisabled(criterion.isDisabled());
            criterionReadDto.setLastModifiedAt(criterion.getLastModifiedAt());
            criterionReadDto.setCriteriaGroupId(criterion.getCriteriaGroup().getId());
            
            return criterionReadDto;

        }).toList();

        // Convert CriteriaComparison entities to DTOs, excluding disabled ones
        List<CriteriaComparisonReadDTO> criteriaComparisonsDTOs = criteriaGroup.getCriteriaComparisons().stream()
            .filter(criteriaComparison -> !criteriaComparison.isDisabled())
            .map(criteriaComparison -> {
                
            CriteriaComparisonReadDTO criteriaComparisonDTO = new CriteriaComparisonReadDTO();
            criteriaComparisonDTO.setId(criteriaComparison.getId());
            criteriaComparisonDTO.setComparedCriterionId(criteriaComparison.getComparedCriterion().getId());
            criteriaComparisonDTO.setReferenceCriterionId(criteriaComparison.getReferenceCriterion().getId());
            criteriaComparisonDTO.setImportanceScale(criteriaComparison.getImportanceScale());
            criteriaComparisonDTO.setDisabled(criteriaComparison.isDisabled());
            criteriaComparisonDTO.setCreatedAt(criteriaComparison.getCreatedAt());
            criteriaComparisonDTO.setLastModifiedAt(criteriaComparison.getLastModifiedAt());
            criteriaComparisonDTO.setCriteriaGroupId(criteriaComparison.getCriteriaGroup().getId());
            // criteriaComparisonDTO.setStrategyId(criteriaComparison.getStrategy().getId());
            
            return criteriaComparisonDTO;
            
            })
            .toList();

        // Create CriteriaGroupReadDTO
        CriteriaGroupReadDTO criteriaGroupReadDto = CriteriaGroupReadDTO.builder()
            .id(criteriaGroup.getId())
            .name(criteriaGroup.getName())
            .description(criteriaGroup.getDescription())
            .criteriaList(criteriaDTOs)
            .criteriaComparisons(criteriaComparisonsDTOs)
            .lastModifiedAt(criteriaGroup.getLastModifiedAt())
            // .lastUpdatedBy(criteriaGroup.getLastUpdatedBy().getId())
            .createdAt(criteriaGroup.getCreatedAt())
            .disabled(criteriaGroup.isDisabled())
            .build();

        return ResponseEntity.ok(criteriaGroupReadDto);

    }

    public ResponseEntity<List<CriteriaGroupListReadDTO>> getCriteriaGroupsByStrategyId(long strategyId, boolean includeDisabled) {

        //TODO: Treat case when strategy is not found

        List<CriteriaGroup> criteriaGroups;
        
        if(includeDisabled){
            
            criteriaGroups = criteriaGroupRepository.findByStrategyId(strategyId);

        } else {

            criteriaGroups = criteriaGroupRepository.findByStrategyIdAndDisabledFalse(strategyId);

        }

        // Convert CriteriaGroup entities to DTOs
        List<CriteriaGroupListReadDTO> criteriaGroupsDTOs = criteriaGroups.stream().map(criteriaGroup -> {

            // Create CriteriaGroupListReadDTO
            CriteriaGroupListReadDTO criteriaGroupReadDto = CriteriaGroupListReadDTO.builder()
                .id(criteriaGroup.getId())
                .name(criteriaGroup.getName())
                .description(criteriaGroup.getDescription())
                .lastModifiedAt(criteriaGroup.getLastModifiedAt())
                .criteriaCount(criteriaGroup.getCriteria().size())
                .criteriaComparisonCount(criteriaGroup.getCriteriaComparisons().size())
                // .lastUpdatedBy(criteriaGroup.getLastUpdatedBy().getId())
                .createdAt(criteriaGroup.getCreatedAt())
                .disabled(criteriaGroup.isDisabled())
                .build();

            return criteriaGroupReadDto;

        }).toList();

        return ResponseEntity.ok(criteriaGroupsDTOs);

    }

    public CriteriaGroup getCriteriaGroupEntityById(
        long strategyId, 
        long criteriaGroupId
        ) {

        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        return criteriaGroup;

    }

    public List<CriteriaGroup> getCriteriaGroupsEntitiesByStrategyId(long strategyId, boolean includeDisabled) {

        //TODO: Treat case when strategy is not found

        List<CriteriaGroup> criteriaGroups;
        
        if(includeDisabled){
            
            criteriaGroups = criteriaGroupRepository.findByStrategyId(strategyId);

        } else {

            criteriaGroups = criteriaGroupRepository.findByStrategyIdAndDisabledFalse(strategyId);

        }

        return criteriaGroups;

    }

}
