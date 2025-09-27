package com.pucpr.portplace.features.resource.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.dtos.resource.ResourceCreateDTO;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceReadDTO;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceUpdateDTO;
import com.pucpr.portplace.features.resource.entities.Position;
import com.pucpr.portplace.features.resource.entities.Resource;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;
import com.pucpr.portplace.features.resource.mappers.ResourceMapper;
import com.pucpr.portplace.features.resource.repositories.ResourceRepository;
import com.pucpr.portplace.features.resource.services.internal.PositionEntityService;
import com.pucpr.portplace.features.resource.services.validation.ResourceValidationService;

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

}
