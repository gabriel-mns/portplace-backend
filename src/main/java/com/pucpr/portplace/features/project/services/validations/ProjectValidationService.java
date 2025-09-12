package com.pucpr.portplace.features.project.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.exceptions.PortfolioNotFoundException;
import com.pucpr.portplace.features.portfolio.specs.PortfolioExistsSpecification;
import com.pucpr.portplace.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.features.project.exceptions.ProjectNotFoundException;
import com.pucpr.portplace.features.project.specs.ProjectExistsSpecification;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectValidationService {

    private ProjectExistsSpecification projectExistsSpecification;
    private PortfolioExistsSpecification portfolioExistsSpecification;

    public void validateBeforeUpdate(long projectId, ProjectUpdateDTO dto) {

        if(!projectExistsSpecification.isSatisfiedBy(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }

    }

    public void validateBeforeCancel(long projectId) {

        if(!projectExistsSpecification.isSatisfiedBy(projectId)) {
            throw new ProjectNotFoundException(projectId);
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
        Long portfolioId
    ) {

        if(portfolioId != null && !portfolioExistsSpecification.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }
    
    }

}
