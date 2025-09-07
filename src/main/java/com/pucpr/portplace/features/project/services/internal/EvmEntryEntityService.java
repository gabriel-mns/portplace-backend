package com.pucpr.portplace.features.project.services.internal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.project.entities.EvmEntry;
import com.pucpr.portplace.features.project.repositories.EvmEntryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvmEntryEntityService {
    
    private EvmEntryRepository repository;

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public EvmEntry findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<EvmEntry> findAllByProjectId(Long projectId) {
        return repository.findByProjectId(projectId);
    }

}