package com.pucpr.portplace.features.resource.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.pucpr.portplace.features.resource.dtos.position.PositionReadDTO;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceCreateDTO;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceReadDTO;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceUpdateDTO;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceWithAvailableHoursProjection;
import com.pucpr.portplace.features.resource.entities.Position;
import com.pucpr.portplace.features.resource.entities.Resource;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;
import com.pucpr.portplace.features.resource.mappers.ResourceMapper;
import com.pucpr.portplace.features.resource.repositories.ResourceRepository;
import com.pucpr.portplace.features.resource.services.internal.AllocationEntityService;
import com.pucpr.portplace.features.resource.services.internal.PositionEntityService;
import com.pucpr.portplace.features.resource.services.validation.ResourceValidationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Service
@AllArgsConstructor
@Getter
@Setter
public class ResourceService {
    
    private ResourceRepository repository;
    private ResourceMapper mapper;
    private ResourceValidationService validationService;
    private PositionEntityService positionEntityService;
    private PositionService positionService;
    private AllocationEntityService allocationService;

    //CREATE
    public ResourceReadDTO create(
        ResourceCreateDTO dto
    ) {

        validationService.validateBeforeCreate(dto.getPositionId());

        Resource entity = mapper.toEntity(dto);
        
        entity = repository.save(entity);
        
        return mapper.toReadDTO(entity);

    }

    //UPDATE
    public ResourceReadDTO update(
        long resourceId,
        ResourceUpdateDTO dto
    ) {

        validationService.validateBeforeUpdate(resourceId, dto.getPositionId());

        Resource entity = repository.findById(resourceId).get();

        mapper.updateFromDTO(dto, entity);

        Position position = positionEntityService.findById(dto.getPositionId());
        entity.setPosition(position);

        entity = repository.save(entity);

        return mapper.toReadDTO(entity);

    }

    //DELETE
    public void disable(
        long resourceId
    ) {

        validationService.validateBeforeDisable(resourceId);

        Resource entity = repository.findById(resourceId).get();

        entity.setDisabled(true);

        repository.save(entity);

        cancelAllocationsOfResource(resourceId);

    }

    public void delete(
        long resourceId
    ) {

        repository.deleteById(resourceId);

    }

    //READ
    public ResourceReadDTO getById(
        long resourceId
    ) {

        validationService.validateBeforeGet(resourceId);

        Resource entity = repository.findById(resourceId).get();

        return mapper.toReadDTO(entity);

    }
    
    public Page<ResourceReadDTO> getAll(
        List<ResourceStatusEnum> status,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        Page<Resource> resources = repository.findByFilters(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return resources.map(mapper::toReadDTO);

    }

    public Page<ResourceReadDTO> getAllWithAvailableHours(
            List<ResourceStatusEnum> status,
            Long positionId,
            Long resourceId,
            Long projectId,
            String searchQuery,
            boolean includeDisabled,
            LocalDate startDate,
            LocalDate endDate,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        List<String> statusList;

        boolean thereAreStatuses = status != null && !status.isEmpty();

        if(thereAreStatuses){
            // Consider only the provided Statuses
            statusList = status.stream().map(Enum::name).toList();
        } else {
            // Consider all Statuses
            statusList = List.of(ResourceStatusEnum.values()).stream().map(Enum::name).toList();
        }

        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ResourceWithAvailableHoursProjection> resources;

        if(sortBy.equals("availableHours")){
            
            resources = sortDir.equalsIgnoreCase("desc") ?
                repository.findByFiltersWithAvailableHoursOrderedByItDesc(
                    statusList,
                    positionId,
                    resourceId,
                    projectId,
                    searchQuery,
                    includeDisabled,
                    startDate,
                    endDate,
                    Pageable.ofSize(size).withPage(page)
                )
                :
                repository.findByFiltersWithAvailableHoursOrderedByItAsc(
                    statusList,
                    positionId,
                    resourceId,
                    projectId,
                    searchQuery,
                    includeDisabled,
                    startDate,
                    endDate,
                    Pageable.ofSize(size).withPage(page)
                );

        } else {
            resources = repository.findByFiltersWithAvailableHours(
                statusList,
                positionId,
                resourceId,
                projectId,
                searchQuery,
                includeDisabled,
                startDate,
                endDate,
                pageable
            );
        }


         return resources.map(r -> {
            ResourceReadDTO dto = new ResourceReadDTO();
            dto.setId(r.getId());
            dto.setName(r.getName());
            dto.setDescription(r.getDescription());
            dto.setDailyHours(r.getDailyHours());
            dto.setStatus(r.getStatus());
            dto.setAvailableHours(r.getAvailableHours());
            dto.setCreatedBy(r.getCreatedBy());
            dto.setLastModifiedBy(r.getLastModifiedBy());
            dto.setCreatedAt(r.getCreatedAt());
            dto.setLastModifiedAt(r.getLastModifiedAt());

            dto.setDisabled(r.getDisabled());
            dto.setRelatedProjectsCount(r.getRelatedProjectsCount());

            PositionReadDTO positionDto = null;
            if (r.getPositionId() != null) {
                positionDto = positionService.getPositionById(r.getPositionId());
            }
            dto.setPosition(positionDto);

            return dto;
        });
    }

    public List<ResourceReadDTO> getAllEntities(
        Long positionId,
        List<ResourceStatusEnum> status,
        String searchQuery,
        boolean includeDisabled
    ) {

        return repository.findByFiltersUnpaged(
            positionId,
            status,
            searchQuery,
            includeDisabled
        ).stream().map(mapper::toReadDTO).toList();

    }

    @Transactional
    public ResourceReadDTO inactivate(Long resourceId) {
        
        validationService.validateBeforeGet(resourceId);

        // Inactive resource
        Resource entity = repository.findById(resourceId).get();
        entity.setStatus(ResourceStatusEnum.INACTIVE);
        entity = repository.save(entity);

        // Cancel all allocations of the resource
        cancelAllocationsOfResource(resourceId);

        return mapper.toReadDTO(entity);

    }

    private void cancelAllocationsOfResource(Long resourceId) {
        allocationService.cancelAllocationsByResourceId(resourceId);
    }

}
