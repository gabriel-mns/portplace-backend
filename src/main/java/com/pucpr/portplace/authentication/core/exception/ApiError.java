package com.pucpr.portplace.authentication.core.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    
    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(HttpStatus status, String message, String path) {
        this.status = status.value();
        this.message = message;
        this.path = path;
    }

}
