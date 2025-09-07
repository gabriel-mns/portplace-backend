package com.pucpr.portplace.features.project.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.project.dtos.EvmEntryCreateDTO;
import com.pucpr.portplace.features.project.dtos.EvmEntryUpdateDTO;
import com.pucpr.portplace.features.project.entities.EvmEntry;
import com.pucpr.portplace.features.project.exceptions.EvmEntryDateConflictException;
import com.pucpr.portplace.features.project.exceptions.EvmEntryNotFoundException;
import com.pucpr.portplace.features.project.exceptions.ProjectNotFoundException;
import com.pucpr.portplace.features.project.services.internal.EvmEntryEntityService;
import com.pucpr.portplace.features.project.specs.EvmEntryExistsSpecification;
import com.pucpr.portplace.features.project.specs.EvmEntryHasValidDateSpecification;
import com.pucpr.portplace.features.project.specs.ProjectExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvmEntryValidationService {
    
    private EvmEntryEntityService service;

    private EvmEntryExistsSpecification evmEntryExistsSpec;
    private ProjectExistsSpecification projectExistsSpec;
    private EvmEntryHasValidDateSpecification evmEntryValidDateSpec;

    public void validateBeforeCreate(
        Long projectId,
        EvmEntryCreateDTO dto
    ) {

        if(!projectExistsSpec.isSatisfiedBy(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }

        int month = dto.getMonth();
        int year = dto.getYear();

        if(evmEntryValidDateSpec.isSatisfiedBy(projectId, month, year)) {
            throw new EvmEntryDateConflictException(projectId, month, year);
        }

    }

    public void validateBeforeUpdate(
        Long projectId,
        Long entryId,
        EvmEntryUpdateDTO dto
    ) {

        if(!evmEntryExistsSpec.isSatisfiedBy(entryId)) {
            throw new ProjectNotFoundException(entryId);
        }

        boolean hasMonth = dto.getMonth() != null;
        boolean hasYear = dto.getYear() != null;

        EvmEntry existingEntry = service.findById(entryId);
        int month = hasMonth ? dto.getMonth() : existingEntry.getMonth();
        int year = hasYear ? dto.getYear() : existingEntry.getYear();

        if(hasMonth || hasYear) {

            if(evmEntryValidDateSpec.isSatisfiedBy(projectId, entryId, month, year)) {
                throw new EvmEntryDateConflictException(projectId, month, year);
            }

        }

    }

    public void validateBeforeDelete(
        Long entryId
    ) {

        if(!evmEntryExistsSpec.isSatisfiedBy(entryId)) {
            throw new ProjectNotFoundException(entryId);
        }

    }

    public void validateBeforeGet(
        Long entryId
    ) {

        if(!evmEntryExistsSpec.isSatisfiedBy(entryId)) {
            throw new EvmEntryNotFoundException(entryId);
        }

    }

    public void validateBeforeGetAll(
        Long projectId
    ) {

        if(!projectExistsSpec.isSatisfiedBy(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }

    }
}
