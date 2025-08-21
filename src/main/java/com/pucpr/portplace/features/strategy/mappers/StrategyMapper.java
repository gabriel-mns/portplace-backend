package com.pucpr.portplace.features.strategy.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.pucpr.portplace.features.strategy.dtos.StrategyCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.Strategy;

@Mapper(
    componentModel = "spring" ,   
    uses = {},
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StrategyMapper {
    
    StrategyReadDTO toReadDTO(Strategy strategy);

    Strategy toEntity(StrategyCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(StrategyUpdateDTO dto, @MappingTarget Strategy entity);

}
