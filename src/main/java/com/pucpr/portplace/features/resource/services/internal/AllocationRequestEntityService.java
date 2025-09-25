package com.pucpr.portplace.features.resource.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.entities.AllocationRequest;
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

}
