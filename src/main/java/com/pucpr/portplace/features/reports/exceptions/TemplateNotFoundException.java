package com.pucpr.portplace.features.reports.exceptions;

public class TemplateNotFoundException extends RuntimeException {
    
    public TemplateNotFoundException(String template) {
        super("Template not found: " + template);
    }
    
}
