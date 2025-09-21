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
import com.pucpr.portplace.features.portfolio.services.validation.RiskValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RiskService {
    
    private RiskRepository repository;
    private RiskMapper mapper;
    private RiskValidationService validationService;

    // CREATE
    public RiskReadDTO createRisk(
        Long portfolioId,
        RiskCreateDTO dto
    ) {

        validationService.validateBeforeCreate(portfolioId);

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

        validationService.validateBeforeUpdate(riskId);

        Risk existingRisk = repository.findById(riskId).get();

        mapper.updateFromDTO(dto, existingRisk);

        existingRisk = repository.save(existingRisk);

        return mapper.toReadDTO(existingRisk);

    }


    // DELETE
    public void disableRisk(
        Long riskId
    ) {

        validationService.validateBeforeDisable(riskId);
        
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

        validationService.validateBeforeGet(riskId);

        Risk existingRisk = repository.findById(riskId).get();

        return mapper.toReadDTO(existingRisk);
    }

    public Page<RiskReadDTO> getAllByPortfolioId(
        Long portfolioId,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        validationService.validateBeforeGetAll(portfolioId);

        Page<Risk> risks = repository.findByFilters(
            portfolioId, 
            searchQuery, 
            includeDisabled, 
            pageable
        );

        return risks.map(mapper::toReadDTO);

    }
}
