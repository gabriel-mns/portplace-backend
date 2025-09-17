package com.pucpr.portplace.core.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pucpr.portplace.features.user.exceptions.UserAlreadyRegisteredException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getMethod(), request.getRequestURI()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusiness(BusinessException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ApiErrorResponse(ex.getStatus(), ex.getMessage(), request.getMethod(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ValidationErrorResponse.FieldValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationErrorResponse.FieldValidationError(
                        error.getField(),
                        error.getRejectedValue() != null ? error.getRejectedValue().toString() : "null",
                        error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                "There are invalid values in your request",
                request.getRequestURI(),
                request.getMethod(),
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            List<Reference> path = invalidFormatException.getPath();
            String fieldName = path.isEmpty() ? "unknown" : path.get(0).getFieldName();
            Object invalidValue = invalidFormatException.getValue();

            var fieldError = new ValidationErrorResponse.FieldValidationError(
                    fieldName,
                    invalidValue,
                    "Invalid value type for field: " + fieldName
            );

            var errorResponse = new ValidationErrorResponse(
                    "There are invalid values in your request",
                    request.getRequestURI(),
                    request.getMethod(),
                    List.of(fieldError)
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // fallback
        var fallbackError = new ValidationErrorResponse(
                "Malformed JSON or invalid values",
                request.getRequestURI(),
                request.getMethod(),
                Collections.emptyList()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fallbackError);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
	var response = new ApiErrorResponse(
					HttpStatus.NOT_FOUND.value(),
					"Endpoint not found",
					ex.getHttpMethod(),
					ex.getRequestURL().toString(),
					LocalDateTime.now()
	);
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

		String suportados = String.join(", ", ex.getSupportedHttpMethods().stream().map(method -> method.name()).toList());

		var response = new ApiErrorResponse(
				HttpStatus.METHOD_NOT_ALLOWED.value(),
				"HTTP method not supported. Allowed methods: " + suportados,
				request.getRequestURI(),
				ex.getMethod(),
				LocalDateTime.now()
		);

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);

	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleTypeMismatch(
			MethodArgumentTypeMismatchException ex,
			HttpServletRequest request
			) {

		String paramName = ex.getName(); // nome do parâmetro
		String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido";
		String providedType = ex.getValue() != null ? ex.getValue().getClass().getSimpleName() : "null";

		String path = request.getRequestURI();

		var response = new ApiErrorResponse(
			HttpStatus.BAD_REQUEST,
			"Invalid value for parameter '" + paramName + "'. Expected type: " + expectedType + ", provided: " + providedType,
			request.getMethod(),
			path
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        
		String message = ex.getMessage();

		var response = new ApiErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				message,
				request.getRequestURI(),
				request.getMethod(),
				LocalDateTime.now()
		);

		return ResponseEntity.badRequest().body(response);
    }

	@ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidSortField(PropertyReferenceException ex, HttpServletRequest request) {

		var response = new ApiErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage(),
				request.getRequestURI(),
				request.getMethod(),
				LocalDateTime.now()
		);

		return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, HttpServletRequest request) {
        String message = ex.getMostSpecificCause().getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), message, request.getRequestURI(), request.getMethod(), LocalDateTime.now()));
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyRegistered(UserAlreadyRegisteredException ex, HttpServletRequest request) {

        var response = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        ex.printStackTrace(); // Log para análise
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", request.getMethod(), request.getRequestURI()));
    }

}
