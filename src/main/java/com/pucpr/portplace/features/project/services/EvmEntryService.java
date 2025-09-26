package com.pucpr.portplace.features.project.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.pucpr.portplace.features.project.dtos.EvmEntryCreateDTO;
import com.pucpr.portplace.features.project.dtos.EvmEntryReadDTO;
import com.pucpr.portplace.features.project.dtos.EvmEntryUpdateDTO;
import com.pucpr.portplace.features.project.entities.EvmEntry;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.project.mappers.EvmEntryMapper;
import com.pucpr.portplace.features.project.repositories.EvmEntryRepository;
import com.pucpr.portplace.features.project.services.internal.ProjectEntityService;
import com.pucpr.portplace.features.project.services.validations.EvmEntryValidationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvmEntryService {

    private ProjectEntityService projectEntityService;

    private EvmEntryRepository repository;
    private EvmEntryMapper mapper;
    private EvmEntryValidationService validationService;

    // CREATE
    @Transactional
    public EvmEntryReadDTO createEvmEntry(
        long projectId, 
        EvmEntryCreateDTO dto
    ) {

        validationService.validateBeforeCreate(projectId, dto);

        // Create and save new EvmEntry
        dto.setProjectId(projectId);
        EvmEntry evmEntry = mapper.toEntity(dto);
        evmEntry = repository.save(evmEntry);

        // Update project calculated values
        Project project = projectEntityService.getProjectEntityById(projectId);
        evmEntry.setProject(project);
        // project.getEvmEntries().add(evmEntry);
        // project.updateCalculatedValues();
        projectEntityService.saveProjectEntity(project);
        
        return mapper.toReadDTO(evmEntry);
    
    }


    // UPDATE
    @Transactional
    public EvmEntryReadDTO updateEvmEntry(
        long projectId,
        long evmEntryId, 
        EvmEntryUpdateDTO dto
    ) {

        validationService.validateBeforeUpdate(projectId,evmEntryId, dto);

        // Update and save EvmEntry
        EvmEntry evmEntry = repository.findById(evmEntryId).get();
        mapper.updateFromDTO(dto, evmEntry);
        evmEntry = repository.save(evmEntry);

        // Update project calculated values
        Project project = projectEntityService.getProjectEntityById(projectId);
        // project.updateCalculatedValues();
        projectEntityService.saveProjectEntity(project);
        
        return mapper.toReadDTO(evmEntry);
    
    }

    // DELETE

    @Transactional
    public void disableEntry(
        long evmEntryId
    ) {

        // Disable EvmEntry
        EvmEntry evmEntry = repository.findById(evmEntryId).get();
        evmEntry.setDisabled(true);
        repository.save(evmEntry);

        // Update project calculated values
        Project project = evmEntry.getProject();
        // project.updateCalculatedValues();
        projectEntityService.saveProjectEntity(project);
    
    }

    public void deleteEntry(
        long projectId,
        long evmEntryId
    ) {

        // Delete EvmEntry
        validationService.validateBeforeDelete(evmEntryId);
        repository.deleteById(evmEntryId);
        
        // Update project calculated values
        Project project = projectEntityService.getProjectEntityById(projectId);
        // project.updateCalculatedValues();
        projectEntityService.saveProjectEntity(project);

    }

    // READ
    public EvmEntryReadDTO getEvmEntryById(
        long evmEntryId
    ) {

        validationService.validateBeforeGet(evmEntryId);

        EvmEntry evmEntry = repository.findById(evmEntryId).get();

        return mapper.toReadDTO(evmEntry);

    }

    public Page<EvmEntryReadDTO> getEvmEntriesByProjectId(
        long projectId,
        boolean includeDisabled,
        Pageable pageable
    ) {

        validationService.validateBeforeGetAll(projectId);

        Page<EvmEntry> evmEntries = repository.findByFilters(projectId, includeDisabled, pageable);

        return evmEntries.map(mapper::toReadDTO);

    }

}
