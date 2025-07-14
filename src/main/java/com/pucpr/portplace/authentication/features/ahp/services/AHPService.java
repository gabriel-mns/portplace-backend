package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.AHPCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.authentication.features.ahp.repositories.AHPRepository;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriteriaGroupEntityService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPService {
    
    private AHPRepository ahpRepository;
    private CriteriaGroupEntityService criteriaGroupEntityService;

    // @Autowired
    // private StrategyService strategyService;

    //CREATE
    public ResponseEntity<Void> createAHP(long strategyId, AHPCreateDTO ahpCreateDto) {
        
        AHP ahp = new AHP();

        CriteriaGroup criteriaGroup = criteriaGroupEntityService.getById(strategyId, ahpCreateDto.getCriteriaGroupId());

        ahp.setName(ahpCreateDto.getName());
        ahp.setDescription(ahpCreateDto.getDescription());
        ahp.setDisabled(false);
        ahp.setCriteriaGroup(criteriaGroup);
        // ahp.setStrategy(null); 
        

        ahpRepository.save(ahp);
    
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    // UPDATE
    public ResponseEntity<Void> updateAHP(long strategyId, Long ahpId, AHPUpdateDTO ahpUpdateDto) {

        // TODO: Treat case when AHP is not found
        AHP ahp = ahpRepository.findById(ahpId).get();

        ahp.setName(ahpUpdateDto.getName());
        ahp.setDescription(ahpUpdateDto.getDescription());

        if( ahpUpdateDto.getCriteriaGroupId() != null ) {

            CriteriaGroup criteriaGroup = criteriaGroupEntityService.getById(strategyId, ahpUpdateDto.getCriteriaGroupId());
            ahp.setCriteriaGroup(criteriaGroup);
        
        }

        ahpRepository.save(ahp);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    // DELETE
    public ResponseEntity<Void> disableAHP(long strategyId, Long id) {
        
        // TODO: Treat case when AHP is not found
        AHP ahp = ahpRepository.findById(id).get();
        ahp.setDisabled(true);
        ahpRepository.save(ahp);
    
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }

    public ResponseEntity<Void> deleteAHP(long strategyId, Long id) {
        
        // TODO: Treat case when AHP is not found
        ahpRepository.deleteById(id);
    
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    // READ
    public ResponseEntity<AHPReadDTO> getAHPById(long strategyId, Long id) {

        AHPReadDTO ahpReadDto = new AHPReadDTO();

        AHP ahp = ahpRepository.findById(id).get();

        ahpReadDto.setId(ahp.getId());
        ahpReadDto.setName(ahp.getName());
        ahpReadDto.setDescription(ahp.getDescription());
        ahpReadDto.setCriteriaGroupId(ahp.getCriteriaGroup().getId());
        ahpReadDto.setCreatedAt(ahp.getCreatedAt());
        ahpReadDto.setLastModifiedAt(ahp.getLastModifiedAt());
        ahpReadDto.setDisabled(ahp.isDisabled());

        return ResponseEntity.ok(ahpReadDto);

    }

    public ResponseEntity<List<AHPReadDTO>> getAllAHPs(long strategyId, boolean includeDisabled) {

        List<AHP> ahps;
        
        if(includeDisabled) {

            ahps = ahpRepository.findAll();

        } else {

            ahps = ahpRepository.findByDisabledFalse();

        }
        
        List<AHPReadDTO> ahpReadDto = ahps.stream().map(ahp ->
            new AHPReadDTO(
                ahp.getId(),
                ahp.getName(),
                ahp.getDescription(),
                ahp.getCriteriaGroup().getId(),
                // ahp.getEvaluations(),
                new ArrayList<>(), // Placeholder for evaluations, should be replaced with actual mapping
                ahp.isDisabled(),
                ahp.getCreatedAt(),
                ahp.getLastModifiedAt()
                // ahp.getLastUpdatedBy()
                
            )
        ).toList();

        return ResponseEntity.ok(ahpReadDto);

    }

    public ResponseEntity<List<AHPReadDTO>> getAHPsByStrategyId(long strategyId, boolean includeDisabled) {

        List<AHP> ahps;
        
        if(includeDisabled) {

            ahps = ahpRepository.findByStrategyId(strategyId);

        } else {

            ahps = ahpRepository.findByStrategyIdAndDisabledFalse(strategyId);

        }
        
        List<AHPReadDTO> ahpReadDto = ahps.stream().map(ahp ->
            new AHPReadDTO(
                ahp.getId(),
                ahp.getName(),
                ahp.getDescription(),
                ahp.getCriteriaGroup().getId(),
                // ahp.getEvaluations(),
                new ArrayList<>(), // Placeholder for evaluations, should be replaced with actual mapping
                ahp.isDisabled(),
                ahp.getCreatedAt(),
                ahp.getLastModifiedAt()
                // ahp.getLastUpdatedBy()
                
            )
        ).toList();

        return ResponseEntity.ok(ahpReadDto);

    }

    public AHP getAHPEntityById(Long id) {

        // TODO: Treat case when AHP is not found
        return ahpRepository.findById(id).get();

    }

}
