package com.pucpr.portplace.features.ahp.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class StrategicObjectiveFromDifferentStrategyException extends BusinessException {

    public StrategicObjectiveFromDifferentStrategyException(long strategicObjectiveId, long strategyId) {
        super("Strategic Objective does not belong to the same Strategy. Strategic Objective ID: " + strategicObjectiveId + ". Strategy ID: " + strategyId , HttpStatus.UNPROCESSABLE_ENTITY);
        //TODO Auto-generated constructor stub
    }
    
}
