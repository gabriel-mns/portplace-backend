package com.pucpr.portplace.features.project.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.project.services.internal.ProjectEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProjectExistsSpecification implements Specification<Long> {

    private final ProjectEntityService projectService;

    @Override
    public boolean isSatisfiedBy(Long projectId) {
        return projectService.existsById(projectId);
    }
    
}
