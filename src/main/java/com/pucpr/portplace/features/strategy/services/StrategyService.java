package com.pucpr.portplace.features.strategy.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.pucpr.portplace.features.strategy.dtos.StrategyCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.Strategy;
import com.pucpr.portplace.features.strategy.enums.StrategyStatusEnum;
import com.pucpr.portplace.features.strategy.mappers.StrategyMapper;
import com.pucpr.portplace.features.strategy.repositories.StrategyRepository;
import com.pucpr.portplace.features.strategy.services.validations.StrategyValidationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategyService {
    
    private StrategyValidationService validationService;
    private StrategyRepository strategyRepository;
    private StrategyMapper strategyMapper;

    //CREATE
    public StrategyReadDTO createStrategy(
        @Valid StrategyCreateDTO dto
    ){

        Strategy strategy = strategyMapper.toEntity(dto);

        Strategy savedStrategy = strategyRepository.save(strategy);

        return strategyMapper.toReadDTO(savedStrategy);

    }

    //UPDATE
    public StrategyReadDTO updateStrategy(
        @Valid StrategyUpdateDTO dto,
        long strategyId
    ){

        validationService.validateBeforeUpdate(strategyId);

        Strategy updatedStrategy = strategyRepository.findById(strategyId).get();

        strategyMapper.updateFromDTO(dto, updatedStrategy);

        updatedStrategy = strategyRepository.save(updatedStrategy);

        StrategyReadDTO response = strategyMapper.toReadDTO(updatedStrategy);

        return response;

    }

    //DELETE
    public void disableStrategy(long strategyId) {

        validationService.validateBeforeDisable(strategyId);

        Strategy disabledStrategy = strategyRepository.findById(strategyId).get();

        disabledStrategy.setDisabled(true);
        disabledStrategy.setStatus(StrategyStatusEnum.INACTIVE);

        strategyRepository.save(disabledStrategy);

    }

    public void deleteStrategy(long strategyId) {

        strategyRepository.deleteById(strategyId);

    }

    //READ
    public Page<StrategyReadDTO> getStrategies(
        List<StrategyStatusEnum> status,
        String searchQuery, 
        boolean includeDisabled,
        Pageable pageable
    ){

        Page<Strategy> strategies = strategyRepository.findByFilters(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return strategies.map(strategyMapper::toReadDTO);

    }

    public StrategyReadDTO getStrategy(
        long strategyId
    ){

        validationService.validateBeforeGet(strategyId);

        Strategy strategy = strategyRepository.findById(strategyId).get();

        return strategyMapper.toReadDTO(strategy);
        
    }

}
