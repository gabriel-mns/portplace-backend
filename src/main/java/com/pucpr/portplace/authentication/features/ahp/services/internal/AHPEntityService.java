package com.pucpr.portplace.authentication.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.repositories.AHPRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPEntityService {
    
    private final AHPRepository ahpRepository;

    public AHP getById(Long id) {

        return ahpRepository.findById(id).get();

    }

    public boolean existsById(Long ahpId) {
        
        return ahpRepository.existsById(ahpId);
        
    }

}
