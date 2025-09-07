package com.pucpr.portplace.features.project.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class EvmEntryDateConflictException extends BusinessException {

    public EvmEntryDateConflictException(Long projectId, int month, int year) {
        super("An EVM Entry for project " + projectId + " in month " + month + " and year " + year + " already exists.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
