package com.pucpr.portplace.features.project.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.exceptions.PortfolioNotFoundException;
import com.pucpr.portplace.features.portfolio.specs.PortfolioExistsSpecification;
import com.pucpr.portplace.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.features.project.exceptions.ProjectNotFoundException;
import com.pucpr.portplace.features.project.specs.ProjectExistsSpecification;
import com.pucpr.portplace.features.user.exceptions.UserNotFoundException;
import com.pucpr.portplace.features.user.specs.UserExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectValidationService {

    private ProjectExistsSpecification projectExistsSpecification;
    private UserExistsSpecification userExistsSpecification;
    private PortfolioExistsSpecification portfolioExistsSpecification;

    public void validateBeforeCreate(ProjectCreateDTO dto) {

        if(!userExistsSpecification.isSatisfiedBy(dto.getProjectManager())) {
            throw new UserNotFoundException(dto.getProjectManager());
        }

    }

    public void validateBeforeUpdate(long projectId, ProjectUpdateDTO dto) {

        if(!projectExistsSpecification.isSatisfiedBy(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }

        if(!userExistsSpecification.isSatisfiedBy(dto.getProjectManager())) {
            throw new UserNotFoundException(dto.getProjectManager());
        }

    }

    public void validateBeforeDelete(long projectId) {

        if(!projectExistsSpecification.isSatisfiedBy(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }

    }

    public void validateBeforeDisable(long projectId) {

        if(!projectExistsSpecification.isSatisfiedBy(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }

    }

    public void validateBeforeGet(long projectId) {

        if(!projectExistsSpecification.isSatisfiedBy(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }

    }

    public void validateBeforeGetAll(
        Long projectManagerId,
        Long portfolioId
    ) {

        if(projectManagerId != null &&!userExistsSpecification.isSatisfiedBy(projectManagerId)) {
            throw new UserNotFoundException(projectManagerId);
        }

        if(portfolioId != null && !portfolioExistsSpecification.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }
    
    }

}
