package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.AHPCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.mappers.AHPMapper;
import com.pucpr.portplace.authentication.features.ahp.repositories.AHPRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPService {
    
    private AHPRepository ahpRepository;
    private AHPMapper ahpMapper;

    // @Autowired
    // private StrategyService strategyService;

    //CREATE
    public AHPReadDTO createAHP(long strategyId, AHPCreateDTO ahpCreateDto) {
        
        AHP ahp = ahpMapper.toEntity(ahpCreateDto);

        ahpRepository.save(ahp);
    
        return ahpMapper.toReadDTO(ahp);
    }

    // UPDATE
    public AHPReadDTO updateAHP(long strategyId, Long ahpId, AHPUpdateDTO ahpUpdateDto) {

        // TODO: Treat case when AHP is not found
        AHP ahp = ahpRepository.findById(ahpId).get();

        // if( ahpUpdateDto.getCriteriaGroupId() != null ) {

        //     CriteriaGroup criteriaGroup = criteriaGroupEntityService.getById(strategyId, ahpUpdateDto.getCriteriaGroupId());
        //     ahp.setCriteriaGroup(criteriaGroup);
        
        // }

        ahpMapper.updateFromDTO(ahpUpdateDto, ahp);

        ahpRepository.save(ahp);

        return ahpMapper.toReadDTO(ahp);
    }

    // DELETE
    public void disableAHP(long strategyId, Long id) {
        
        // TODO: Treat case when AHP is not found
        AHP ahp = ahpRepository.findById(id).get();
        ahp.setDisabled(true);
        ahpRepository.save(ahp);
        
    }

    public void deleteAHP(long strategyId, Long id) {
        
        // TODO: Treat case when AHP is not found
        ahpRepository.deleteById(id);

    }

    // READ
    public AHPReadDTO getAHPById(long strategyId, Long id) {
        
        AHP ahp = ahpRepository.findById(id).get();

        AHPReadDTO ahpReadDto = ahpMapper.toReadDTO(ahp);

        return ahpReadDto;

    }

    public List<AHPReadDTO> getAllAHPs(long strategyId, boolean includeDisabled) {

        List<AHP> ahps;
        
        if(includeDisabled) {

            ahps = ahpRepository.findAll();

        } else {

            ahps = ahpRepository.findByDisabledFalse();

        }

        List<AHPReadDTO> ahpReadDTOs = ahpMapper.toReadDTO(ahps);

        return ahpReadDTOs;

    }

    public ResponseEntity<List<AHPReadDTO>> getAHPsByStrategyId(long strategyId, boolean includeDisabled) {

        List<AHP> ahps;
        
        if(includeDisabled) {

            ahps = ahpRepository.findByStrategyId(strategyId);

        } else {

            ahps = ahpRepository.findByStrategyIdAndDisabledFalse(strategyId);

        }
        
        List<AHPReadDTO> ahpReadDto = ahpMapper.toReadDTO(ahps);

        return ResponseEntity.ok(ahpReadDto);

    }

    public AHP getAHPEntityById(Long id) {

        // TODO: Treat case when AHP is not found
        return ahpRepository.findById(id).get();

    }

}
