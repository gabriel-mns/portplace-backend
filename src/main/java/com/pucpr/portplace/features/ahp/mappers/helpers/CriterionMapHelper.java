package com.pucpr.portplace.features.ahp.mappers.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.features.ahp.services.internal.CriteriaGroupEntityService;
@Component
public class CriterionMapHelper {
 
    @Autowired
    private CriteriaGroupEntityService criteriaGroupEntityService;
    
    public CriteriaGroup fromId(Long id) {

        return criteriaGroupEntityService.getById(id);

    }
    
}
