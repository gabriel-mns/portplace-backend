package com.pucpr.portplace.features.ahp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.AHPCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.AHPReadDTO;
import com.pucpr.portplace.features.ahp.dtos.AHPUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.AHP;
import com.pucpr.portplace.features.ahp.mappers.AHPMapper;
import com.pucpr.portplace.features.ahp.repositories.AHPRepository;
import com.pucpr.portplace.features.ahp.services.validations.AHPValidationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPService {
    
    private AHPRepository ahpRepository;
    private AHPMapper ahpMapper;

    private AHPValidationService validationService;

    //CREATE
    public AHPReadDTO createAHP(long strategyId, AHPCreateDTO ahpCreateDto) {

        validationService.validateBeforeCreation(strategyId, ahpCreateDto);

        ahpCreateDto.setStrategyId(strategyId);

        AHP ahp = ahpMapper.toEntity(ahpCreateDto);

        ahpRepository.save(ahp);
    
        return ahpMapper.toReadDTO(ahp);
    }

    // UPDATE
    @Transactional
    public AHPReadDTO updateAHP(long strategyId, Long ahpId, AHPUpdateDTO ahpUpdateDTO) {

        validationService.validateBeforeUpdate(strategyId, ahpId, ahpUpdateDTO);

        AHP ahp = ahpRepository.findById(ahpId).get();

        ahpUpdateDTO.setStrategyId(ahp.getStrategy().getId());

        ahpMapper.updateFromDTO(ahpUpdateDTO, ahp);

        ahpRepository.save(ahp);

        return ahpMapper.toReadDTO(ahp);
    }

    // DELETE
    public void disableAHP(long strategyId, Long id) {
        
        validationService.validateBeforeDisable(strategyId, id);

        AHP ahp = ahpRepository.findById(id).get();
        ahp.setDisabled(true);
        ahpRepository.save(ahp);
        
    }

    public void deleteAHP(long strategyId, Long id) {
        
        validationService.validateBeforeDelete(strategyId, id);
        
        ahpRepository.deleteById(id);

    }

    // READ
    public AHPReadDTO getAHPById(long strategyId, Long id) {
        
        AHP ahp = ahpRepository.findById(id).get();

        AHPReadDTO ahpReadDto = ahpMapper.toReadDTO(ahp);

        return ahpReadDto;

    }

    public Page<AHPReadDTO> getAllAHPs(long strategyId, String name, boolean includeDisabled, Pageable pageable) {

        Page<AHP> ahps;

        boolean containsName = name != null && !name.isEmpty();
        
        if(containsName) {
            
            ahps = ahpRepository.findByName(name, includeDisabled, pageable);
            
        } else {

            if(includeDisabled) {
    
                ahps = ahpRepository.findAll(pageable);
    
            } else {
    
                ahps = ahpRepository.findByDisabledFalse(pageable);
    
            }

        }


        Page<AHPReadDTO> ahpReadDTOs = ahps.map(ahpMapper::toReadDTO);

        return ahpReadDTOs;

    }

}
