package com.pucpr.portplace.features.project.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.project.dtos.EvmEntryCreateDTO;
import com.pucpr.portplace.features.project.dtos.EvmEntryReadDTO;
import com.pucpr.portplace.features.project.dtos.EvmEntryUpdateDTO;
import com.pucpr.portplace.features.project.entities.EvmEntry;
import com.pucpr.portplace.features.project.mappers.EvmEntryMapper;
import com.pucpr.portplace.features.project.repositories.EvmEntryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvmEntryService {
    
    private EvmEntryRepository repository;
    private EvmEntryMapper mapper;

    // CREATE
    public EvmEntryReadDTO createEvmEntry(
        long projectId, 
        EvmEntryCreateDTO dto
    ) {

        // TODO: Check if project exists
        // TODO: Check if there is already an EVM Entry for the given month and year

        dto.setProjectId(projectId);

        EvmEntry evmEntry = mapper.toEntity(dto);

        evmEntry = repository.save(evmEntry);
        
        return mapper.toReadDTO(evmEntry);
    
    }


    // UPDATE
    public EvmEntryReadDTO updateEvmEntry(
        long evmEntryId, 
        EvmEntryUpdateDTO dto
    ) {

        // TODO: Check if EVM Entry exists

        EvmEntry evmEntry = repository.findById(evmEntryId).get();

        mapper.updateFromDTO(dto, evmEntry);

        evmEntry = repository.save(evmEntry);
        
        return mapper.toReadDTO(evmEntry);
    
    }

    // DELETE

    public void disableEntry(
        long evmEntryId
    ) {

        // TODO: Check if EVM Entry exists

        EvmEntry evmEntry = repository.findById(evmEntryId).get();

        evmEntry.setDisabled(true);

        repository.save(evmEntry);
    
    }

    public void deleteEntry(
        long evmEntryId
    ) {

        // TODO: Check if EVM Entry exists

        repository.deleteById(evmEntryId);

    }

    // READ
    public EvmEntryReadDTO getEvmEntryById(
        long evmEntryId
    ) {

        // TODO: Check if EVM Entry exists

        EvmEntry evmEntry = repository.findById(evmEntryId).get();

        return mapper.toReadDTO(evmEntry);

    }

    public Page<EvmEntryReadDTO> getEvmEntriesByProjectId(
        long projectId,
        boolean includeDisabled,
        Pageable pageable
    ) {

        // TODO: Check if project exists
        
        Page<EvmEntry> evmEntries = repository.findByFilters(projectId, includeDisabled, pageable);

        return evmEntries.map(mapper::toReadDTO);

    }
}
