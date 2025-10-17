package com.pucpr.portplace.features.resource.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.repositories.AllocationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AllocationEntityService {
    
    private final AllocationRepository allocationRepository;

    public boolean existsById(Long allocationId) {
        return allocationRepository.existsById(allocationId);
    }

}
