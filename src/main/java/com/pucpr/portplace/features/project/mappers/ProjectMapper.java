package com.pucpr.portplace.features.project.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.pucpr.portplace.features.portfolio.mappers.PortfolioCategoryMapper;
import com.pucpr.portplace.features.portfolio.mappers.PortfolioMapper;
import com.pucpr.portplace.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.user.mappers.UserMapper;

@Mapper(
    componentModel = "spring", 
    uses = {UserMapper.class, PortfolioCategoryMapper.class, PortfolioMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectMapper {
    
    // Create
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "budgetAtCompletion", source = "dto.budgetAtCompletion")
    @Mapping(target = "status", constant = "IN_ANALYSIS")
    Project toEntity(ProjectCreateDTO dto);

    // READ
    @Mapping(target = "strategicObjectives", ignore = true)
    @Mapping(target = "evaluations", ignore = true)
    ProjectReadDTO toReadDTO(Project entity);

    List<ProjectReadDTO> toReadDTO(List<Project> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(ProjectUpdateDTO dto, @MappingTarget Project entity);
    
}
