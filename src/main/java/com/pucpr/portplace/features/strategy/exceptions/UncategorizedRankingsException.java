package com.pucpr.portplace.features.strategy.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class UncategorizedRankingsException extends BusinessException{

    public UncategorizedRankingsException(long scenarioId) {
        super("There are uncategorized rankings in the scenario with ID: " + scenarioId, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}

