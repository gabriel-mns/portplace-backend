package com.pucpr.portplace.authentication.features.project.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.authentication.features.project.exceptions.ProjectNotFoundException;
import com.pucpr.portplace.authentication.features.project.specs.ProjectExistsSpecification;
import com.pucpr.portplace.authentication.features.user.dtos.UserRegisterDTO;
import com.pucpr.portplace.authentication.features.user.exceptions.UserNotFoundException;
import com.pucpr.portplace.authentication.features.user.specs.UserExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectValidationService {

    private ProjectExistsSpecification projectExistsSpecification;
    private UserExistsSpecification userExistsSpecification;

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

    public void validateBeforeGetByProjectManagerId(long projectManagerId) {

        if(!userExistsSpecification.isSatisfiedBy(projectManagerId)) {
            throw new UserNotFoundException(projectManagerId);
        }

    }

}
