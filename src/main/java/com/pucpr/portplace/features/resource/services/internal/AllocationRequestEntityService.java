package com.pucpr.portplace.features.resource.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.entities.AllocationRequest;
import com.pucpr.portplace.features.resource.enums.AllocationRequestStatusEnum;
import com.pucpr.portplace.features.resource.repositories.AllocationRequestRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AllocationRequestEntityService {
    
    private AllocationRequestRepository repository;

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public AllocationRequest findById(Long id) {
        return repository.findById(id).get();
    }

    public void cancelRequestByResourceId(Long resourceId) {
        repository.cancelRequestByResourceId(resourceId);
    }

    public void cancelRequestByAllocationId(Long allocationId) {
        repository.cancelRequestByAllocationId(allocationId);
    }

    public void markAsAllocated(Long id) {
        AllocationRequest entity = findById(id);
        entity.setStatus(AllocationRequestStatusEnum.ALLOCATED);
        repository.save(entity);
    }

    public void markAsInAnalysis(Long allocationRequestId) {
        AllocationRequest entity = findById(allocationRequestId);
        entity.setStatus(AllocationRequestStatusEnum.IN_ANALYSIS);
        repository.save(entity);
    }

    public void markAsCancelled(Long allocationRequestId) {
        AllocationRequest entity = findById(allocationRequestId);
        entity.setStatus(AllocationRequestStatusEnum.CANCELLED);
        repository.save(entity);
    }

}
