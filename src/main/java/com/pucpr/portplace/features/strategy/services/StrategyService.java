package com.pucpr.portplace.features.strategy.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.pucpr.portplace.features.strategy.dtos.StrategyCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.Strategy;
import com.pucpr.portplace.features.strategy.mappers.StrategyMapper;
import com.pucpr.portplace.features.strategy.repositories.StrategyRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategyService {
    
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

        //TODO: validate if strategy exists

        Strategy updatedStrategy = strategyRepository.findById(strategyId).get();

        strategyMapper.updateFromDTO(dto, updatedStrategy);

        updatedStrategy = strategyRepository.save(updatedStrategy);

        StrategyReadDTO response = strategyMapper.toReadDTO(updatedStrategy);

        return response;

    }

    //DELETE
    public void disableStrategy(long strategyId) {

        //TODO: validate if strategy exists

        Strategy disabledStrategy = strategyRepository.findById(strategyId).get();

        disabledStrategy.setDisabled(true);

        strategyRepository.save(disabledStrategy);

    }

    public void deleteStrategy(long strategyId) {

        //TODO: validate if strategy exists

        strategyRepository.deleteById(strategyId);

    }

    //READ
    public Page<StrategyReadDTO> getStrategies(
        boolean includeDisabled,
        Pageable pageable
    ){

        Page<Strategy> strategies;

        if(includeDisabled){
            strategies = strategyRepository.findAll(pageable);
        } else {
            strategies = strategyRepository.findByDisabledFalse(pageable);
        }

        return strategies.map(strategyMapper::toReadDTO);

    }

    public StrategyReadDTO getStrategy(
        long id
    ){

        //TODO: check if strategy exists

        Strategy strategy = strategyRepository.findById(id).get();

        return strategyMapper.toReadDTO(strategy);
        
    }

}
