package com.pucpr.portplace.features.portfolio.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurenceCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurrenceReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurrenceUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.RiskOccurrence;
import com.pucpr.portplace.features.portfolio.enums.RiskOccurrenceStatusEnum;
import com.pucpr.portplace.features.portfolio.mappers.RiskOccurrenceMapper;
import com.pucpr.portplace.features.portfolio.repositories.RiskOccurrenceRepository;
import com.pucpr.portplace.features.portfolio.services.validation.RiskOccurrenceValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RiskOccurenceService {
    
    private RiskOccurrenceRepository repository;
    private RiskOccurrenceMapper mapper;
    private RiskOccurrenceValidationService validationService;

    //CREATE
    public RiskOccurrenceReadDTO create(
        Long riskId,
        RiskOccurenceCreateDTO dto
    ) {

        validationService.validateBeforeCreate(riskId);
        
        dto.setRiskId(riskId);

        RiskOccurrence newOccurrence = mapper.toEntity(dto);

        return mapper.toReadDTO(repository.save(newOccurrence));
    }

    //UPDATE
    public RiskOccurrenceReadDTO update(
        Long occurrenceId,
        RiskOccurrenceUpdateDTO dto
    ) {

        validationService.validateBeforeUpdate(occurrenceId);

        RiskOccurrence occurrence = repository.findById(occurrenceId).get();
        
        mapper.updateFromDTO(dto, occurrence);

        return mapper.toReadDTO(repository.save(occurrence));
    }

    //DELETE
    public void disable(
        Long occurrenceId
    ){

        validationService.validateBeforeDisable(occurrenceId);

        RiskOccurrence occurrence = repository.findById(occurrenceId).get();
        occurrence.setDisabled(true);
        repository.save(occurrence);

    }

    public void delete(
        Long occurrenceId
    ){
        repository.deleteById(occurrenceId);
    }

    //READ
    public RiskOccurrenceReadDTO findById(
        Long occurrenceId
    ){

        validationService.validateBeforeGet(occurrenceId);

        RiskOccurrence occurrence = repository.findById(occurrenceId).get();

        return mapper.toReadDTO(occurrence);

    }

    public Page<RiskOccurrenceReadDTO> findAll(
        Long riskId,
        List<RiskOccurrenceStatusEnum> status,
        boolean includeDisabled,
        String searchQuery,
        Pageable pageable
    ){

        validationService.validateBeforeGetAll(riskId);

        Page<RiskOccurrence> occurrences = repository.findByFilters(
            riskId,
            status,
            includeDisabled,
            searchQuery,
            pageable
        );

        return occurrences.map(occurrence -> mapper.toReadDTO(occurrence));

    }
    
}
