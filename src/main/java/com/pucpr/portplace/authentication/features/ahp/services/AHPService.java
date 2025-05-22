package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.AHPReadDto;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.repositories.AHPRepository;

@Service
public class AHPService {
    
    @Autowired
    private AHPRepository ahpRepository;

    //CREATE
    public ResponseEntity<Void> createAHP() {
        
        ahpRepository.save(new AHP());
    
        return ResponseEntity.ok().build();

    }

    // DELETE
    public ResponseEntity<Void> disableAHP(Long id) {
        
        // TODO: Treat case when AHP is not found
        AHP ahp = ahpRepository.findById(id).get();
        ahp.setDisabled(true);
        ahpRepository.save(ahp);
    
        return ResponseEntity.ok().build();
        
    }

    public ResponseEntity<Void> deleteAHP(Long id) {
        
        // TODO: Treat case when AHP is not found
        ahpRepository.deleteById(id);
    
        return ResponseEntity.ok().build();

    }

    // READ
    public ResponseEntity<AHPReadDto> getAHP(Long id) {

        AHPReadDto ahpReadDto = new AHPReadDto();

        AHP ahp = ahpRepository.findById(id).get();

        ahpReadDto.setId(ahp.getId());
        ahpReadDto.setCreatedAt(ahp.getCreatedAt());
        ahpReadDto.setDisabled(ahp.isDisabled());
        // ahpReadDto.setProjects(ahp.getProjects());
        // ahpReadDto.setCriteria(ahp.getCriteria());
        // ahpReadDto.setCriteriaComparisons(ahp.getCriteriaComparisons());
        // ahpReadDto.setEvaluations(ahp.getEvaluations());


        return ResponseEntity.ok(ahpReadDto);

    }

    public ResponseEntity<List<AHPReadDto>> getAllAHPs(boolean includeDisabled) {

        List<AHP> ahps;
        
        if(includeDisabled) {

            ahps = ahpRepository.findAll();

        } else {

            ahps = ahpRepository.findByDisabledFalse();

        }
        
        List<AHPReadDto> ahpReadDto = ahps.stream().map(ahp ->
            new AHPReadDto(
                ahp.getId(),
                ahp.getCreatedAt(),
                ahp.isDisabled(),
                new ArrayList<>(), // Placeholder for projects
                new ArrayList<>(), // Placeholder for criteria
                new ArrayList<>(), // Placeholder for criteria comparisons
                new ArrayList<>()  // Placeholder for evaluations
            )
        ).toList();

        return ResponseEntity.ok(ahpReadDto);

    }

}
