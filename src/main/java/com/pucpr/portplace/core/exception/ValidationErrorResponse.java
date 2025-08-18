package com.pucpr.portplace.core.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "status", "message", "path", "method", "timestamp", "errors" })
public class ValidationErrorResponse extends ApiErrorResponse {

    private List<FieldValidationError> errors;

    public ValidationErrorResponse(String message, String path, String method, List<FieldValidationError> errors) {
        super(400, message, path, method, LocalDateTime.now());
        this.errors = errors;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FieldValidationError {
        private String field;
        private Object value;
        private String message;

    }
    
}
