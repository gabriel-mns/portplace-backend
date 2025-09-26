package com.pucpr.portplace.features.portfolio.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.entities.Risk;
import com.pucpr.portplace.features.portfolio.repositories.RiskRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RiskEntityService {
    
    private RiskRepository riskRepository;

    public boolean existsById(Long id) {
        return riskRepository.existsById(id);
    }

    public void save(Risk risk) {
        riskRepository.save(risk);
    }

    public Risk findById(Long id) {
        return riskRepository.findById(id).get();
    }


}
