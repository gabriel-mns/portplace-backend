package com.pucpr.portplace.authentication.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.repositories.AHPRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPEntityService {
    
    private final AHPRepository ahpRepository;

    public AHP getAHPEntityById(Long id) {

        // TODO: Treat case when AHP is not found
        return ahpRepository.findById(id).get();

    }

}
