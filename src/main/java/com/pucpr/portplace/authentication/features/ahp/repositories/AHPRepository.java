package com.pucpr.portplace.authentication.features.ahp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.AHP;

public interface AHPRepository extends JpaRepository<AHP, Long> {
    
}
