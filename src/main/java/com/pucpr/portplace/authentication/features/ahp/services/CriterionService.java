package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriterionRepository;

@Service
public class CriterionService {

    @Autowired
    private AHPService ahpService;

    @Autowired
    private CriterionRepository criterionRepository;

    // CREATE
    public ResponseEntity<Void> createCriterion(long AHPId, CriterionCreateDTO criterionCreateDTO) {
        
        Criterion criterion = new Criterion();

        AHP ahp = ahpService.getAHPEntityById(AHPId);

        criterion.setName(criterionCreateDTO.getName());
        criterion.setDescription(criterionCreateDTO.getDescription());
        criterion.setAhp(ahp);

        criterionRepository.save(criterion);
        
        return ResponseEntity.ok().build();

    }

    // UPDATE
    public ResponseEntity<Void> updateCriterion(Long id, CriterionUpdateDTO criterionCreateDTO) {

        //TODO: Treat case when criterion is not found
        Criterion criterion = criterionRepository.findById(id).get();

        criterion.setName(criterionCreateDTO.getName());
        criterion.setDescription(criterionCreateDTO.getDescription());

        criterionRepository.save(criterion);

        return ResponseEntity.ok().build();

    }

    // DELETE
    public ResponseEntity<Void> disableCriterion(Long id) {

        //TODO: Treat case when criterion is not found
        Criterion criterion = criterionRepository.findById(id).get();

        criterion.setDisabled(true);

        criterionRepository.save(criterion);

        return ResponseEntity.ok().build();

    }

    public ResponseEntity<Void> deleteCriterion(Long id) {

        //TODO: Treat case when criterion is not found
        criterionRepository.deleteById(id);

        return ResponseEntity.ok().build();

    }

    // READ
    public ResponseEntity<CriterionReadDTO> getCriterionById(Long id) {

        Criterion criterion = criterionRepository.findById(id).get();
        CriterionReadDTO criterionReadDto = new CriterionReadDTO();

        criterionReadDto.setId(criterion.getId());
        criterionReadDto.setName(criterion.getName());
        criterionReadDto.setDescription(criterion.getDescription());
        criterionReadDto.setAhpId(criterion.getAhp().getId());

        return ResponseEntity.ok(criterionReadDto);

    }

    public ResponseEntity<Criterion> getCriterionEntityById(Long id) {

        Criterion criterion = criterionRepository.findById(id).get();

        return ResponseEntity.ok(criterion);

    }

    public ResponseEntity<List<CriterionReadDTO>> getCriteriaByAHPId(long ahpId, boolean includeDisabled) {

        List<Criterion> criteria;

        if(includeDisabled) {

            criteria = criterionRepository.findByAhpId(ahpId);

        } else {

            criteria = criterionRepository.findByAhpIdAndDisabledFalse(ahpId);

        }

        List<CriterionReadDTO> criteriaDTOs = criteria.stream().map(criterion -> {
            CriterionReadDTO criterionReadDto = new CriterionReadDTO();
            criterionReadDto.setId(criterion.getId());
            criterionReadDto.setName(criterion.getName());
            criterionReadDto.setDescription(criterion.getDescription());
            criterionReadDto.setAhpId(criterion.getAhp().getId());
            return criterionReadDto;
        }).toList();

        return ResponseEntity.ok(criteriaDTOs);

    }

}
