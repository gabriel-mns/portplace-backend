package com.pucpr.portplace.features.resource.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.entities.Position;
import com.pucpr.portplace.features.resource.repositories.PositionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PositionEntityService {
    
    private PositionRepository positionRepository;

    public boolean existsById(Long id) {
        return positionRepository.existsById(id);
    }

    public void save(Position position) {
        positionRepository.save(position);
    }

}
