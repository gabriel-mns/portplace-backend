package com.pucpr.portplace.authentication.features.project.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.pucpr.portplace.authentication.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.authentication.features.project.entities.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    
    // Create
    @Mapping(target = "projectManager.id", source = "projectManager")
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    Project toEntity(ProjectCreateDTO dto);

    // READ
    // @Mapping(target = "projectManager", source = "projectManager.id")
    ProjectReadDTO toReadDTO(Project entity);

    // @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // @Mapping(target = "projectManager.id", source = "projectManagerId")
    // void updateFromDTO(ProjectUpdateDTO dto, @MappingTarget Project entity);
}
