package com.pucpr.portplace.features.project.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.pucpr.portplace.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.user.mappers.UserMapper;

@Mapper(
    componentModel = "spring", 
    uses = UserMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectMapper {
    
    // Create
    @Mapping(target = "projectManager.id", source = "projectManager")
    @Mapping(target = "id", ignore = true)
    Project toEntity(ProjectCreateDTO dto);

    // READ
    // @Mapping(target = "projectManager", source = "projectManager.id")
    ProjectReadDTO toReadDTO(Project entity);

    List<ProjectReadDTO> toReadDTO(List<Project> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "projectManager.id", source = "projectManager")
    void updateFromDTO(ProjectUpdateDTO dto, @MappingTarget Project entity);
}
