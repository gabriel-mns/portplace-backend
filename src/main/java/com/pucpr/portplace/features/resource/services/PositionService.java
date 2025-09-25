package com.pucpr.portplace.features.resource.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.dtos.position.PositionCreateDTO;
import com.pucpr.portplace.features.resource.dtos.position.PositionReadDTO;
import com.pucpr.portplace.features.resource.dtos.position.PositionUpdateDTO;
import com.pucpr.portplace.features.resource.entities.Position;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;
import com.pucpr.portplace.features.resource.mappers.PositionMapper;
import com.pucpr.portplace.features.resource.repositories.PositionRepository;
import com.pucpr.portplace.features.resource.services.validation.PositionValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PositionService {
    
    private final PositionRepository repository;
    private final PositionMapper mapper;
    private final PositionValidationService validationService;

    //CREATE
    public PositionReadDTO createPosition(
        PositionCreateDTO dto
    ) {

        Position position = mapper.toEntity(dto);

        position = repository.save(position);

        return mapper.toReadDTO(position);
    }

    //UPDATE
    public PositionReadDTO updatePosition(
        Long positionId, 
        PositionUpdateDTO dto
    ) {
        
        validationService.validateBeforeUpdate(positionId);

        Position existingPosition = repository.findById(positionId).get();

        mapper.updateFromDTO(dto, existingPosition);

        repository.save(existingPosition);

        return mapper.toReadDTO(existingPosition);
    }

    //DELETE
    public void disablePosition(
        Long positionId
    ) {
       
        validationService.validateBeforeDelete(positionId);
        
        Position existingPosition = repository.findById(positionId).get();

        existingPosition.setStatus(ResourceStatusEnum.INACTIVE);
        existingPosition.setDisabled(true);

        repository.save(existingPosition);

    }

    public void deletePosition(
        Long positionId
    ) {
        repository.deleteById(positionId);
    }

    //READ
    public PositionReadDTO getPositionById(
        Long positionId
    ) {

        validationService.validateBeforeGet(positionId);

        Position position = repository.findById(positionId).get();

        return mapper.toReadDTO(position);

    }

    public Page<PositionReadDTO> getAllPositions(
        List<ResourceStatusEnum> status,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {
        
        Page<Position> positions = repository.findByFilters(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return positions.map(mapper::toReadDTO);
        
    }

}
