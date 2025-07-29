package com.pucpr.portplace.features.ahp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupListReadDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupReadDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.features.ahp.mappers.CriteriaGroupMapper;
import com.pucpr.portplace.features.ahp.repositories.CriteriaGroupRepository;
import com.pucpr.portplace.features.ahp.repositories.StrategyRepository;
import com.pucpr.portplace.features.ahp.services.validations.CriteriaGroupValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriteriaGroupService {
    
    private CriteriaGroupRepository criteriaGroupRepository;
    private CriteriaGroupMapper criteriaGroupMapper;
    private StrategyRepository strategyRepository;

    private CriteriaGroupValidationService validationService;
    

    // CREATE
    public CriteriaGroupReadDTO createCriteriaGroup(
        long strategyId,
        CriteriaGroupCreateDTO criteriaGroupCreateDto
        ) {

        // TODO: Check if criteria will be added to the group on group creation
        
        validationService.validateBeforeCreation(strategyId);

        CriteriaGroup criteriaGroup = criteriaGroupMapper.toCriteriaGroupEntity(criteriaGroupCreateDto);
        //TODO: Change to use strategyService;
        criteriaGroup.setStrategy(strategyRepository.findById(strategyId).get());

        CriteriaGroup entityCreated = criteriaGroupRepository.save(criteriaGroup);
        CriteriaGroupReadDTO criteriaGroupReadDTO = criteriaGroupMapper.toCriteriaGroupReadDTO(entityCreated);

        return criteriaGroupReadDTO;

    }

    // UPDATE
    public CriteriaGroupReadDTO updateCriteriaGroup(
        long strategyId,
        long criteriaGroupId,
        CriteriaGroupUpdateDTO criteriaGroupCreateDto
        ) {

        validationService.validateBeforeUpdate(strategyId, criteriaGroupId);
        
        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        criteriaGroupMapper.updateFromDTO(criteriaGroupCreateDto, criteriaGroup);
        criteriaGroupRepository.save(criteriaGroup);
        CriteriaGroupReadDTO criteriaGroupReadDTO = criteriaGroupMapper.toCriteriaGroupReadDTO(criteriaGroup);

        return criteriaGroupReadDTO;

    }

    // DELETE
    public void disableCriteriaGroup(long strategyId, long criteriaGroupId) {

        validationService.validateBeforeDelete(strategyId, criteriaGroupId);

        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        criteriaGroup.setDisabled(true);

        criteriaGroupRepository.save(criteriaGroup);

    }

    public void deleteCriteriaGroup(long strategyId, long criteriaGroupId) {

        validationService.validateBeforeDelete(strategyId, criteriaGroupId);
        
        criteriaGroupRepository.deleteById(criteriaGroupId);

    }

    // READ
    public CriteriaGroupReadDTO getCriteriaGroupById(long strategyId, long criteriaGroupId) {

        validationService.validateBeforeGet(strategyId, criteriaGroupId);

        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        CriteriaGroupReadDTO dto = criteriaGroupMapper.toCriteriaGroupReadDTO(criteriaGroup);

        return dto;

    }

    public Page<CriteriaGroupListReadDTO> getCriteriaGroupsByStrategyId(long strategyId, boolean includeDisabled, Pageable pageable) {

        validationService.validateBeforeGetAll(strategyId);

        Page<CriteriaGroup> criteriaGroups;

        if(includeDisabled){

            criteriaGroups = criteriaGroupRepository.findByStrategyId(strategyId, pageable);

        } else {

            criteriaGroups = criteriaGroupRepository.findByStrategyIdAndDisabledFalse(strategyId, pageable);

        }

        Page<CriteriaGroupListReadDTO> criteriaGroupsDTOs = criteriaGroups.map(criteriaGroupMapper::toCriteriaGroupListReadDTO);

        return criteriaGroupsDTOs;

    }

}
