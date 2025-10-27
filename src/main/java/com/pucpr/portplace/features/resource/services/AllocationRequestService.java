package com.pucpr.portplace.features.resource.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.dtos.request.AllocationRequestCreateDTO;
import com.pucpr.portplace.features.resource.dtos.request.AllocationRequestReadDTO;
import com.pucpr.portplace.features.resource.dtos.request.AllocationRequestUpdateDTO;
import com.pucpr.portplace.features.resource.entities.AllocationRequest;
import com.pucpr.portplace.features.resource.entities.Position;
import com.pucpr.portplace.features.resource.enums.AllocationRequestStatusEnum;
import com.pucpr.portplace.features.resource.mappers.AllocationRequestMapper;
import com.pucpr.portplace.features.resource.repositories.AllocationRequestRepository;
import com.pucpr.portplace.features.resource.services.internal.AllocationEntityService;
import com.pucpr.portplace.features.resource.services.internal.PositionEntityService;
import com.pucpr.portplace.features.resource.services.validation.AllocationRequestValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AllocationRequestService {

    private final PositionEntityService positionEntityService;
    private final AllocationEntityService allocationService;
    private final AllocationRequestRepository repository;
    private final AllocationRequestMapper mapper;
    private final AllocationRequestValidationService validationService;

    //CREATE
    public AllocationRequestReadDTO create(
        AllocationRequestCreateDTO dto
    ) {

        validationService.validateBeforeCreate(dto);

        AllocationRequest entity = mapper.toEntity(dto);

        repository.save(entity);

        return mapper.toReadDTO(entity);

    }

    //UPDATE
    public AllocationRequestReadDTO update(
        Long allocationRequestId,
        AllocationRequestUpdateDTO dto
    ) {

        validationService.validateBeforeUpdate(allocationRequestId, dto.getPositionId());

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

        validationService.validateBeforeDisable(allocationRequestId);

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

        validationService.validateBeforeGet(allocationRequestId);

        AllocationRequest entity = repository.findById(allocationRequestId).get();

        return mapper.toReadDTO(entity);
    
    }

    public Page<AllocationRequestReadDTO> findAll(
        Long resourceId, 
        List<AllocationRequestStatusEnum> status,
        String searchQuery, 
        boolean includeDisabled, 
        Pageable pageable) {
        
        Page<AllocationRequest> page = repository.findByFilters(
            resourceId,
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return page.map(mapper::toReadDTO);

    }

    public void cancel(Long requestId) {
        
        validationService.validateBeforeGet(requestId);

        repository.cancelRequest(requestId);
        allocationService.cancelAllocationByRequestId(requestId);
        // // Update request
        // AllocationRequest allocationRequest = repository.findById(requestId).get();
        // allocationRequest.setStatus(AllocationRequestStatusEnum.CANCELLED);

        // // Update allocation if exists
        // boolean wasAllocated = allocationRequest.getAllocation() != null;
        // if(wasAllocated) allocationRequest.getAllocation().setCancelled(true);

        // allocationRequest = repository.save(allocationRequest);

        // return mapper.toReadDTO(allocationRequest);

    }

}
