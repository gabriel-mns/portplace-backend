package com.pucpr.portplace.features.resource.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.entities.Resource;
import com.pucpr.portplace.features.resource.repositories.ResourceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResourceEntityService {
    
    private final ResourceRepository resourceRepository;

    public boolean existsById(Long id) {
        return resourceRepository.existsById(id);
    }

    public void save(Resource entity) {
        resourceRepository.save(entity);
    }

}
