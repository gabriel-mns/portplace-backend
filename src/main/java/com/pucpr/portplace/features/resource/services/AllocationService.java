package com.pucpr.portplace.features.resource.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.dtos.allocation.AllocationCreateDTO;
import com.pucpr.portplace.features.resource.dtos.allocation.AllocationReadDTO;
import com.pucpr.portplace.features.resource.dtos.allocation.AllocationUpdateDTO;
import com.pucpr.portplace.features.resource.entities.Allocation;
import com.pucpr.portplace.features.resource.entities.AllocationRequest;
import com.pucpr.portplace.features.resource.entities.Resource;
import com.pucpr.portplace.features.resource.mappers.AllocationMapper;
import com.pucpr.portplace.features.resource.repositories.AllocationRepository;
import com.pucpr.portplace.features.resource.services.internal.AllocationRequestEntityService;
import com.pucpr.portplace.features.resource.services.internal.ResourceEntityService;
import com.pucpr.portplace.features.resource.services.validation.AllocationValidationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AllocationService {
    
    private AllocationRepository allocationRepository;
    private AllocationMapper mapper;
    private ResourceEntityService resourceService;
    private AllocationRequestEntityService allocationRequestService;
    private AllocationValidationService validationService;

    //CREATE
    @Transactional
    public AllocationReadDTO create(
        AllocationCreateDTO dto
    ) {

        validationService.validateBeforeCreate(dto);

        Allocation newAllocation = mapper.toEntity(dto);
        
        // Set request
        AllocationRequest allocationRequest = allocationRequestService.findById(dto.getAllocationRequestId());
        newAllocation.setAllocationRequest(allocationRequest);
        
        // Set resource
        Resource resource = resourceService.getById(dto.getResourceId());
        newAllocation.setResource(resource);

        newAllocation = allocationRepository.save(newAllocation);

        allocationRequestService.markAsAllocated(dto.getAllocationRequestId());

        return mapper.toReadDTO(newAllocation);

    }

    //UPDATE
    public AllocationReadDTO update(
        Long allocationId,
        AllocationUpdateDTO dto
    ) {

        //TODO: validate if allocation exists
        //TODO: validate if resource exists

        validationService.validateBeforeUpdate(allocationId, dto);

        Allocation allocation = allocationRepository.findById(allocationId).get();

        mapper.updateFromDTO(dto, allocation);

        var resource = resourceService.getById(dto.getResourceId());
        allocation.setResource(resource);

        allocation = allocationRepository.save(allocation);

        return mapper.toReadDTO(allocation);
    }

    //DELETE
    @Transactional
    public void disable(
        Long allocationId
    ) {

        validationService.validateBeforeDelete(allocationId);
        
        Allocation allocation = allocationRepository.findById(allocationId).get();

        AllocationRequest request = allocation.getAllocationRequest();
        request.setAllocation(null);
        allocationRequestService.markAsInAnalysis(request.getId());
        
        
        allocation.setAllocationRequest(null);
        allocation.setResource(null);
        
        allocationRepository.delete(allocation);

    }

    @Transactional
    public void delete(
        Long allocationId
    ) {

        allocationRepository.deleteById(allocationId);

    }

    //READ
    public AllocationReadDTO getById(
        Long allocationId
    ) {

        validationService.validateBeforeGet(allocationId);

        Allocation allocation = allocationRepository.findById(allocationId).get();

        return mapper.toReadDTO(allocation);

    }

    public Page<AllocationReadDTO> getAll(
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        Page<Allocation> allocations = allocationRepository.findByFilters(
            searchQuery,
            includeDisabled,
            pageable
        );

        return allocations.map(mapper::toReadDTO);

    }

}
