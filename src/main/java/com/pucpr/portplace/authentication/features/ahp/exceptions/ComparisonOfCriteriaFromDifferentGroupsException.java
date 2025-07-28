package com.pucpr.portplace.authentication.features.ahp.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.authentication.core.exception.BusinessException;

public class ComparisonOfCriteriaFromDifferentGroupsException extends BusinessException {

    public ComparisonOfCriteriaFromDifferentGroupsException(long criterion1Id, long criterion2Id) {
        super("Comparison of criteria from different criteria groups is not allowed. Criterion 1 ID: " + criterion1Id + ", Criterion 2 ID: " + criterion2Id, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
