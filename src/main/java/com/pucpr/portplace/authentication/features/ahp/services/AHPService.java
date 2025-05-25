package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.AHPReadDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.repositories.AHPRepository;

@Service
public class AHPService {
    
    @Autowired
    private AHPRepository ahpRepository;

    //CREATE
    public ResponseEntity<Void> createAHP() {
        // AHPCreateDTO ahpCreateDto


        // AHP ahp = AHP.builder()
        //     .criteriaGroup()
        //     .disabled(false)
        //     // .projects(ahpCreateDto.getProjects()) // Assuming projects are handled elsewhere
        //     .build();

        ahpRepository.save(new AHP());
    
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    // DELETE
    public ResponseEntity<Void> disableAHP(Long id) {
        
        // TODO: Treat case when AHP is not found
        AHP ahp = ahpRepository.findById(id).get();
        ahp.setDisabled(true);
        ahpRepository.save(ahp);
    
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }

    public ResponseEntity<Void> deleteAHP(Long id) {
        
        // TODO: Treat case when AHP is not found
        ahpRepository.deleteById(id);
    
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    // READ
    public ResponseEntity<AHPReadDTO> getAHPById(Long id) {

        AHPReadDTO ahpReadDto = new AHPReadDTO();

        AHP ahp = ahpRepository.findById(id).get();

        ahpReadDto.setId(ahp.getId());
        // ahpReadDto.setCreatedAt(ahp.getCreatedAt());
        ahpReadDto.setDisabled(ahp.isDisabled());
        // ahpReadDto.setProjects(ahp.getProjects());
        // ahpReadDto.setCriteria(ahp.getCriteria());
        // ahpReadDto.setCriteriaComparisons(ahp.getCriteriaComparisons());
        // ahpReadDto.setEvaluations(ahp.getEvaluations());


        return ResponseEntity.ok(ahpReadDto);

    }

    public ResponseEntity<List<AHPReadDTO>> getAllAHPs(boolean includeDisabled) {

        List<AHP> ahps;
        
        if(includeDisabled) {

            ahps = ahpRepository.findAll();

        } else {

            ahps = ahpRepository.findByDisabledFalse();

        }
        
        List<AHPReadDTO> ahpReadDto = ahps.stream().map(ahp ->
            new AHPReadDTO(
                ahp.getId(),
                ahp.getCriteriaGroup().getId(),
                // ahp.getEvaluations(),
                new ArrayList<>(), // Placeholder for evaluations, should be replaced with actual mapping
                ahp.isDisabled()
                // ahp.getCreatedAt(),
                // ahp.getLastUpdatedAt(),
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
