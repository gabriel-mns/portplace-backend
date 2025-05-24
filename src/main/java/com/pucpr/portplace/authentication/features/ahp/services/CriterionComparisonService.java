package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriteriaComparisonRepository;

@Service
public class CriterionComparisonService {
    
    @Autowired
    private CriteriaComparisonRepository criteriaComparisonRepository;

    @Autowired
    private CriterionService criterionService;

    @Autowired
    private AHPService ahpService;

    // CREATE
    public ResponseEntity<Void> createCriteriaComparison(CriteriaComparisonCreateDTO criteriaComparisonCreateDTO, long AHPId) {
        
        CriteriaComparison newCriteriaComparison = new CriteriaComparison();

        Criterion comparedCriterion = criterionService.getCriterionEntityById(criteriaComparisonCreateDTO.getComparedCriterionId());
        Criterion referenceCriterion = criterionService.getCriterionEntityById(criteriaComparisonCreateDTO.getReferenceCriterionId());
        AHP ahp = ahpService.getAHPEntityById(AHPId);

        //TODO: Treat case when comparedCriterion and referenceCriterion are not found
        //TODO: Treat case when ahp is not found
        //TODO: Treat case when comparedCriterion and referenceCriterion are the same
        //TODO: Treat case when comparedCriterion and referenceCriterion are already compared
        //TODO: Treat case when comparedCriterion and referenceCriterion are already compared in the opposite direction
        //TODO: Treat case when comparedCriterion and referenceCriterion are already compared in the opposite direction with different importanceScale
        //TODO: Treat case when comparedCriterion or referenceCriterion are not in the same AHP

        //TODO: Create a parser from CriteriaComparisonCreateDTO to CriteriaComparison
        newCriteriaComparison.setComparedCriterion(comparedCriterion);
        newCriteriaComparison.setReferenceCriterion(referenceCriterion);
        newCriteriaComparison.setImportanceScale(criteriaComparisonCreateDTO.getImportanceScale());
        newCriteriaComparison.setAhp(ahp);

        criteriaComparisonRepository.save(newCriteriaComparison);

        return ResponseEntity.status(HttpStatus.CREATED).build();
        
    }

    // UPDATE
    public ResponseEntity<Void> updateCriteriaComparison(long criteriaComparisonId, CriteriaComparisonUpdateDTO criteriaComparisonCreateDTO) {

        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();

        //TODO: Treat case when criteriaComparison is disabled
        //TODO: Treat case when criteriaComparison was not found

        criteriaComparison.setImportanceScale(criteriaComparisonCreateDTO.getImportanceScale());
        
        criteriaComparisonRepository.save(criteriaComparison);
        
        return ResponseEntity.ok().build();
    
    }

    // DELETE
    public ResponseEntity<Void> disableCriteriaComparison(long criteriaComparisonId) {

        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();

        //TODO: Treat case when criteriaComparison is already disabled
        //TODO: Treat case when criteriaComparison was not found

        criteriaComparison.setDisabled(true);
        
        criteriaComparisonRepository.save(criteriaComparison);
        
        return ResponseEntity.ok().build();
    
    }

    public ResponseEntity<Void> deleteCriteriaComparison(long criteriaComparisonId) {

        //TODO: Treat case when criteriaComparison is already disabled
        //TODO: Treat case when criteriaComparison was not found

        criteriaComparisonRepository.deleteById(criteriaComparisonId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    
    }

    // READ
    public ResponseEntity<CriteriaComparisonReadDTO> getCriteriaComparisonById(long criteriaComparisonId) {

        CriteriaComparisonReadDTO criteriaComparisonDTO = new CriteriaComparisonReadDTO();

        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();
    
        //TODO: Treat case when criteriaComparison was not found

        criteriaComparisonDTO.setId(criteriaComparison.getId());
        criteriaComparisonDTO.setComparedCriterionId(criteriaComparison.getComparedCriterion().getId());
        criteriaComparisonDTO.setReferenceCriterionId(criteriaComparison.getReferenceCriterion().getId());
        criteriaComparisonDTO.setImportanceScale(criteriaComparison.getImportanceScale());
        criteriaComparisonDTO.setAhpId(criteriaComparison.getAhp().getId());
        criteriaComparisonDTO.setDisabled(criteriaComparison.isDisabled());

        return ResponseEntity.ok(criteriaComparisonDTO);

    }

    public CriteriaComparison getCriteriaComparisonEntityById(long criteriaComparisonId) {

        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();
    
        //TODO: Treat case when criteriaComparison was not found

        return criteriaComparison;

    }

    public ResponseEntity<List<CriteriaComparisonReadDTO>> getCriteriaComparisons(long ahpId, Long comparedCriterionId, Long referenceCriterionId, boolean includeDisabled) {

        boolean hasComparedCriterion = comparedCriterionId != null;
        boolean hasReferenceCriterion = referenceCriterionId != null;

        if (hasComparedCriterion && hasReferenceCriterion) {
            return getCriteriaComparisonByComparedCriterionIdAndReferenceCriterionIdAndAhpId(comparedCriterionId, referenceCriterionId, ahpId, includeDisabled);
        } else if (hasComparedCriterion) {
            return getCriteriaComparisonByComparedCriterionIdAndAhpId(comparedCriterionId, ahpId, includeDisabled);
        } else if (hasReferenceCriterion) {
            return getCriteriaComparisonByReferenceCriterionIdAndAhpId(referenceCriterionId, ahpId, includeDisabled);
        } else {
            return getCriteriaComparisonByAhpId(ahpId, includeDisabled);
        }
    
    }

    public ResponseEntity<List<CriteriaComparisonReadDTO>> getCriteriaComparisonByComparedCriterionIdAndAhpId(long comparedCriterionId, long ahpId, boolean includeDisabled) {

        List<CriteriaComparison> criteriaComparisonList;

        if(includeDisabled){

            criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndAhpId(comparedCriterionId, ahpId);

        } else {

            criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndAhpIdAndDisabledFalse(comparedCriterionId, ahpId);

        }

        List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonList.stream()
                .map(criteriaComparison -> {
                    CriteriaComparisonReadDTO criteriaComparisonDTO = new CriteriaComparisonReadDTO();
                    criteriaComparisonDTO.setId(criteriaComparison.getId());
                    criteriaComparisonDTO.setComparedCriterionId(criteriaComparison.getComparedCriterion().getId());
                    criteriaComparisonDTO.setReferenceCriterionId(criteriaComparison.getReferenceCriterion().getId());
                    criteriaComparisonDTO.setImportanceScale(criteriaComparison.getImportanceScale());
                    criteriaComparisonDTO.setAhpId(criteriaComparison.getAhp().getId());
                    criteriaComparisonDTO.setDisabled(criteriaComparison.isDisabled());
                    return criteriaComparisonDTO;
                })
                .toList();

        return ResponseEntity.ok(criteriaComparisonDTOList);
    
    }

    public ResponseEntity<List<CriteriaComparisonReadDTO>> getCriteriaComparisonByReferenceCriterionIdAndAhpId(long referenceCriterionId, long ahpId, boolean includeDisabled) {

        List<CriteriaComparison> criteriaComparisonList;

        if(includeDisabled){

            criteriaComparisonList = criteriaComparisonRepository.findByReferenceCriterionIdAndAhpId(referenceCriterionId, ahpId);

        } else {

            criteriaComparisonList = criteriaComparisonRepository.findByReferenceCriterionIdAndAhpIdAndDisabledFalse(referenceCriterionId, ahpId);

        }

        List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonList.stream()
                .map(criteriaComparison -> {
                    CriteriaComparisonReadDTO criteriaComparisonDTO = new CriteriaComparisonReadDTO();
                    criteriaComparisonDTO.setId(criteriaComparison.getId());
                    criteriaComparisonDTO.setComparedCriterionId(criteriaComparison.getComparedCriterion().getId());
                    criteriaComparisonDTO.setReferenceCriterionId(criteriaComparison.getReferenceCriterion().getId());
                    criteriaComparisonDTO.setImportanceScale(criteriaComparison.getImportanceScale());
                    criteriaComparisonDTO.setAhpId(criteriaComparison.getAhp().getId());
                    criteriaComparisonDTO.setDisabled(criteriaComparison.isDisabled());
                    return criteriaComparisonDTO;
                })
                .toList();

        return ResponseEntity.ok(criteriaComparisonDTOList);
    
    }

    public ResponseEntity<List<CriteriaComparisonReadDTO>> getCriteriaComparisonByComparedCriterionIdAndReferenceCriterionIdAndAhpId(long comparedCriterionId, long referenceCriterionId, long ahpId, boolean includeDisabled) {

        List<CriteriaComparison> criteriaComparisonList;

        if(includeDisabled){

            criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndReferenceCriterionIdAndAhpId(comparedCriterionId, referenceCriterionId, ahpId);

        } else {

            criteriaComparisonList = criteriaComparisonRepository.findByComparedCriterionIdAndReferenceCriterionIdAndAhpIdAndDisabledFalse(comparedCriterionId, referenceCriterionId, ahpId);

        }

        List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonList.stream()
                .map(criteriaComparison -> {
                    CriteriaComparisonReadDTO criteriaComparisonDTO = new CriteriaComparisonReadDTO();
                    criteriaComparisonDTO.setId(criteriaComparison.getId());
                    criteriaComparisonDTO.setComparedCriterionId(criteriaComparison.getComparedCriterion().getId());
                    criteriaComparisonDTO.setReferenceCriterionId(criteriaComparison.getReferenceCriterion().getId());
                    criteriaComparisonDTO.setImportanceScale(criteriaComparison.getImportanceScale());
                    criteriaComparisonDTO.setAhpId(criteriaComparison.getAhp().getId());
                    criteriaComparisonDTO.setDisabled(criteriaComparison.isDisabled());
                    return criteriaComparisonDTO;
                })
                .toList();

        return ResponseEntity.ok(criteriaComparisonDTOList);
    
    }

    public ResponseEntity<List<CriteriaComparisonReadDTO>> getCriteriaComparisonByAhpId(long ahpId, boolean includeDisabled) {

        List<CriteriaComparison> criteriaComparisonList;

        if(includeDisabled){

            criteriaComparisonList = criteriaComparisonRepository.findByAhpId(ahpId);

        } else {

            criteriaComparisonList = criteriaComparisonRepository.findByAhpIdAndDisabledFalse(ahpId);

        }

        List<CriteriaComparisonReadDTO> criteriaComparisonDTOList = criteriaComparisonList.stream()
                .map(criteriaComparison -> {
                    CriteriaComparisonReadDTO criteriaComparisonDTO = new CriteriaComparisonReadDTO();
                    criteriaComparisonDTO.setId(criteriaComparison.getId());
                    criteriaComparisonDTO.setComparedCriterionId(criteriaComparison.getComparedCriterion().getId());
                    criteriaComparisonDTO.setReferenceCriterionId(criteriaComparison.getReferenceCriterion().getId());
                    criteriaComparisonDTO.setImportanceScale(criteriaComparison.getImportanceScale());
                    criteriaComparisonDTO.setAhpId(criteriaComparison.getAhp().getId());
                    criteriaComparisonDTO.setDisabled(criteriaComparison.isDisabled());
                    return criteriaComparisonDTO;
                })
                .toList();

        return ResponseEntity.ok(criteriaComparisonDTOList);
    
    }

}
