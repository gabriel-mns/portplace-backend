package com.pucpr.portplace.features.portfolio.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.risk.RiskCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.RiskReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.RiskUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.Risk;
import com.pucpr.portplace.features.portfolio.mappers.RiskMapper;
import com.pucpr.portplace.features.portfolio.repositories.RiskRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RiskService {
    
    private RiskRepository repository;
    private RiskMapper mapper;

    // CREATE
    public RiskReadDTO createRisk(
        Long portfolioId,
        RiskCreateDTO dto
    ) {

        //TODO: validate if portfolio exists

        dto.setPortfolioId(portfolioId);

        Risk newRisk = mapper.toEntity(dto);
        
        newRisk = repository.save(newRisk);

        return mapper.toReadDTO(newRisk);

    }

    // UPDATE
    public RiskReadDTO updateRisk(
        Long riskId,
        RiskUpdateDTO dto
    ) {

        //TODO: validate if risk exists
        Risk existingRisk = repository.findById(riskId).get();

        mapper.updateFromDTO(dto, existingRisk);

        existingRisk = repository.save(existingRisk);

        return mapper.toReadDTO(existingRisk);

    }


    // DELETE
    public void disableRisk(
        Long riskId
    ) {

        //TODO: validate if risk exists
        Risk existingRisk = repository.findById(riskId).get();

        existingRisk.setDisabled(true);

        existingRisk = repository.save(existingRisk);

    }

    public void deleteRisk(
        Long riskId
    ) {
        repository.deleteById(riskId);
    }
    
    // READ
    public RiskReadDTO getById(
        Long riskId
    ) {

        Risk existingRisk = repository.findById(riskId).get();

        return mapper.toReadDTO(existingRisk);
    }

    public Page<RiskReadDTO> getAllByPortfolioId(
        Long portfolioId,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        Page<Risk> risks = repository.findByFilters(
            portfolioId, 
            searchQuery, 
            includeDisabled, 
            pageable
        );

        return risks.map(mapper::toReadDTO);

    }
}
