package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupListReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.authentication.features.ahp.mappers.CriteriaGroupMapper;
// import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriteriaGroupRepository;
import com.pucpr.portplace.authentication.features.ahp.repositories.StrategyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriteriaGroupService {
    
    private CriteriaGroupRepository criteriaGroupRepository;
    private CriteriaGroupMapper criteriaGroupMapper;
    private StrategyRepository strategyRepository;
    

    // CREATE
    public CriteriaGroupReadDTO createCriteriaGroup(long strategyId, CriteriaGroupCreateDTO criteriaGroupCreateDto) {

        // List<Long> criteriaIdList = criteriaGroupCreateDto.getCriteriaIdList();
        
        
        CriteriaGroup criteriaGroup = criteriaGroupMapper.toCriteriaGroupEntity(criteriaGroupCreateDto);
        criteriaGroup.setStrategy(strategyRepository.findById(strategyId).get()); //TODO: Change to use strategyService;

        // if( criteriaIdList != null && !criteriaIdList.isEmpty() ) {
            
        //     List<Criterion> criteriaList = criteriaIdList.stream()
        //         .map(id -> criterionService.getCriterionEntityById(id))
        //         .toList();

        //     criteriaGroup.setCriteria(criteriaList);

        // }

        CriteriaGroup entityCreated = criteriaGroupRepository.save(criteriaGroup);
        CriteriaGroupReadDTO criteriaGroupReadDTO = criteriaGroupMapper.toCriteriaGroupReadDTO(entityCreated);

        return criteriaGroupReadDTO;

    }

    // UPDATE
    public CriteriaGroupReadDTO updateCriteriaGroup(long strategyId, long criteriaGroupId, CriteriaGroupUpdateDTO criteriaGroupCreateDto) {

        //TODO: Treat not found exception
        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        // List<Long> criteriaIdList = criteriaGroupCreateDto.getCriteriaIdList();

        // if( criteriaIdList != null && !criteriaIdList.isEmpty() ) {
            
        //     List<Criterion> criteriaList = criteriaIdList.stream()
        //         .map(criterionService::getCriterionEntityById)
        //         .toList();

        //     criteriaGroup.setCriteria(criteriaList);

        // }

        criteriaGroupMapper.updateFromDTO(criteriaGroupCreateDto, criteriaGroup);
        criteriaGroupRepository.save(criteriaGroup);
        CriteriaGroupReadDTO criteriaGroupReadDTO = criteriaGroupMapper.toCriteriaGroupReadDTO(criteriaGroup);

        return criteriaGroupReadDTO;

    }

    // DELETE
    public void disableCriteriaGroup(long strategyId, long criteriaGroupId) {

        //TODO: Treat not found exception
        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        criteriaGroup.setDisabled(true);

        criteriaGroupRepository.save(criteriaGroup);

    }

    public void deleteCriteriaGroup(long strategyId, long criteriaGroupId) {

        //TODO: Treat not found exception
        criteriaGroupRepository.deleteById(criteriaGroupId);

    }

    // READ
    public CriteriaGroupReadDTO getCriteriaGroupById(long strategyId, long criteriaGroupId) {

        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        CriteriaGroupReadDTO dto = criteriaGroupMapper.toCriteriaGroupReadDTO(criteriaGroup);

        return dto;

    }

    public List<CriteriaGroupListReadDTO> getCriteriaGroupsByStrategyId(long strategyId, boolean includeDisabled) {

        //TODO: Treat case when strategy is not found

        List<CriteriaGroup> criteriaGroups;
        
        if(includeDisabled){
            
            criteriaGroups = criteriaGroupRepository.findByStrategyId(strategyId);

        } else {

            criteriaGroups = criteriaGroupRepository.findByStrategyIdAndDisabledFalse(strategyId);

        }

        List<CriteriaGroupListReadDTO> criteriaGroupsDTOs = criteriaGroupMapper.toCriteriaGroupListReadDTO(criteriaGroups);

        return criteriaGroupsDTOs;

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
