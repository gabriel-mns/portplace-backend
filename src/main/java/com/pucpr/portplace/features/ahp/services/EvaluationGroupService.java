package com.pucpr.portplace.features.ahp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupReadDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.ahp.enums.EvaluationGroupStatusEnum;
import com.pucpr.portplace.features.ahp.mappers.EvaluationGroupMapper;
import com.pucpr.portplace.features.ahp.repositories.EvaluationGroupRepository;
import com.pucpr.portplace.features.ahp.services.validations.EvaluationGroupValidationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationGroupService {
    
    private EvaluationGroupRepository egRepository;
    private EvaluationGroupMapper egMapper;

    private EvaluationGroupValidationService validationService;

    //CREATE
    public EvaluationGroupReadDTO createEvaluationGroup(long strategyId, EvaluationGroupCreateDTO egCreateDto) {

        validationService.validateBeforeCreation(strategyId, egCreateDto);

        egCreateDto.setStrategyId(strategyId);

        EvaluationGroup eg = egMapper.toEntity(egCreateDto);

        egRepository.save(eg);
    
        return egMapper.toReadDTO(eg);
    }

    // UPDATE
    @Transactional
    public EvaluationGroupReadDTO updateEvaluationGroup(long strategyId, Long egId, EvaluationGroupUpdateDTO egUpdateDTO) {

        validationService.validateBeforeUpdate(strategyId, egId, egUpdateDTO);

        EvaluationGroup eg = egRepository.findById(egId).get();

        egUpdateDTO.setStrategyId(eg.getStrategy().getId());

        egMapper.updateFromDTO(egUpdateDTO, eg);

        egRepository.save(eg);

        return egMapper.toReadDTO(eg);
    }

    // DELETE
    public void disableEvaluationGroup(long strategyId, Long id) {
        
        validationService.validateBeforeDisable(strategyId, id);

        EvaluationGroup eg = egRepository.findById(id).get();

        eg.setDisabled(true);
        eg.setStatus(EvaluationGroupStatusEnum.INACTIVE);

        egRepository.save(eg);
        
    }

    public void deleteEvaluationGroup(long strategyId, Long id) {
        
        validationService.validateBeforeDelete(strategyId, id);
        
        egRepository.deleteById(id);

    }

    // READ
    public EvaluationGroupReadDTO getEvaluationGroupById(long strategyId, Long id) {
        
        EvaluationGroup eg = egRepository.findById(id).get();

        EvaluationGroupReadDTO egReadDto = egMapper.toReadDTO(eg);

        return egReadDto;

    }

    public Page<EvaluationGroupReadDTO> getAllEvaluationGroups(
        long strategyId, 
        List<EvaluationGroupStatusEnum> status, 
        String name, 
        boolean includeDisabled, 
        Pageable pageable
    ) {

        Page<EvaluationGroup> evaluationGroups = egRepository.findByFilters(status, name, includeDisabled, pageable);

        Page<EvaluationGroupReadDTO> egReadDTOs = evaluationGroups.map(egMapper::toReadDTO);

        return egReadDTOs;

    }

}
