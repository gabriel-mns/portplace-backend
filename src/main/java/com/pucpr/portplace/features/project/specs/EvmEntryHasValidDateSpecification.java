package com.pucpr.portplace.features.project.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.features.project.services.internal.EvmEntryEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EvmEntryHasValidDateSpecification {

    private EvmEntryEntityService service;

    public boolean isSatisfiedBy(long projectId, int month, int year) {

        return service.findAllByProjectId(projectId)
            .stream()
            .anyMatch(entry -> entry.getMonth() == month &&
                               entry.getYear() == year &&
                              !entry.isDisabled());

    }

    public boolean isSatisfiedBy(long projectId, long entryId, int month, int year) {

        return service.findAllByProjectId(projectId)
            .stream()
            .anyMatch(entry -> entry.getMonth() == month &&
                               entry.getYear() == year &&
                               entry.getId() != entryId &&
                              !entry.isDisabled());

    }

}
