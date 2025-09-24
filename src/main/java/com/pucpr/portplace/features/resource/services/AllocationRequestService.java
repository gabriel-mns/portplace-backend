package com.pucpr.portplace.features.resource.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.dtos.AllocationRequestCreateDTO;
import com.pucpr.portplace.features.resource.dtos.AllocationRequestReadDTO;
import com.pucpr.portplace.features.resource.dtos.AllocationRequestUpdateDTO;
import com.pucpr.portplace.features.resource.entities.AllocationRequest;
import com.pucpr.portplace.features.resource.entities.Position;
import com.pucpr.portplace.features.resource.enums.AllocationRequestStatusEnum;
import com.pucpr.portplace.features.resource.mappers.AllocationRequestMapper;
import com.pucpr.portplace.features.resource.repositories.AllocationRequestRepository;
import com.pucpr.portplace.features.resource.services.internal.PositionEntityService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AllocationRequestService {

    private final PositionEntityService positionEntityService;
    private final AllocationRequestRepository repository;
    private final AllocationRequestMapper mapper;

    //CREATE
    public AllocationRequestReadDTO create(
        AllocationRequestCreateDTO dto
    ) {

        //TODO: validate if the position exists

        AllocationRequest entity = mapper.toEntity(dto);

        repository.save(entity);

        return mapper.toReadDTO(entity);

    }

    //UPDATE
    public AllocationRequestReadDTO update(
        Long allocationRequestId,
        AllocationRequestUpdateDTO dto
    ) {

        //TODO: validate if the allocationRequest exists
        //TODO: validate if the new position exists

        AllocationRequest entity = repository.findById(allocationRequestId).get();

        Position newPosition = positionEntityService.findById(dto.getPositionId());

        entity.setPosition(newPosition);

        mapper.updateFromDTO(dto, entity);

        repository.save(entity);

        return mapper.toReadDTO(entity);

    }

    //DELETE
    public void disable(
        Long allocationRequestId
    ) {

        //TODO: validate if the allocationRequest exists

        AllocationRequest entity = repository.findById(allocationRequestId).get();

        entity.setDisabled(true);

        repository.save(entity);

    }

    public void delete(
        Long allocationRequestId
    ) {

        repository.deleteById(allocationRequestId);

    }

    //READ
    public AllocationRequestReadDTO findById(
        Long allocationRequestId
    ) {

        AllocationRequest entity = repository.findById(allocationRequestId).get();

        return mapper.toReadDTO(entity);
    
    }
    
    public Page<AllocationRequestReadDTO> findAll(
        List<AllocationRequestStatusEnum> status,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        Page<AllocationRequest> page = repository.findByFilters(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return page.map(mapper::toReadDTO);
    
    }


}
