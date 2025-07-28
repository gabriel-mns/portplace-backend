package com.pucpr.portplace.authentication.features.project.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.validation.specs.Specification;
import com.pucpr.portplace.authentication.features.project.services.internal.ProjectEntityService;

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
