package com.pucpr.portplace.features.portfolio.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.stakeholder.StakeholderCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.stakeholder.StakeholderReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.stakeholder.StakeholderUpdateDTO;
import com.pucpr.portplace.features.portfolio.mappers.StakeholderMapper;
import com.pucpr.portplace.features.portfolio.repositories.StakeholderRepository;
import com.pucpr.portplace.features.portfolio.services.validation.StakeholderValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StakeholderService {
    
    private StakeholderRepository repository;
    private StakeholderMapper mapper;
    private StakeholderValidationService validationService;

    //CREATE
    public StakeholderReadDTO createStakeholder(
        Long portfolioId, 
        StakeholderCreateDTO stakeholderCreateDTO
    ) {

        validationService.validateBeforeCreate(portfolioId);

        stakeholderCreateDTO.setPortfolioId(portfolioId);

        var stakeholder = mapper.toEntity(stakeholderCreateDTO);

        stakeholder = repository.save(stakeholder);

        return mapper.toReadDTO(stakeholder);

    }

    //UPDATE
    public StakeholderReadDTO updateStakeholder(
        Long stakeholderId, 
        StakeholderUpdateDTO dto
    ) {
        
        validationService.validateBeforeUpdate(stakeholderId);

        var stakeholder = repository.findById(stakeholderId).get();

        mapper.updateFromDTO(dto, stakeholder);

        stakeholder = repository.save(stakeholder);

        return mapper.toReadDTO(stakeholder);

    }

    //DELETE
    public void disableStakeholder(
        Long stakeholderId
    ) {

        validationService.validateBeforeDelete(stakeholderId);

        var stakeholder = repository.findById(stakeholderId).get();

        stakeholder.setDisabled(true);

        repository.save(stakeholder);

    }

    public void deleteStakeholder(
        Long stakeholderId
    ) {

        repository.deleteById(stakeholderId);

    }

    //READ
    public StakeholderReadDTO getStakeholderById(
        Long stakeholderId
    ) {

        validationService.validateBeforeGet(stakeholderId);

        var stakeholder = repository.findById(stakeholderId).get();

        return mapper.toReadDTO(stakeholder);

    }

    public Page<StakeholderReadDTO> getStakeholders(
        Long portfolioId,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        validationService.validateBeforeGetAll(portfolioId);
        
        var stakeholders = repository.findByFilters(
            portfolioId,
            searchQuery,
            includeDisabled,
            pageable
        );

        return stakeholders.map(mapper::toReadDTO);

    }

}
