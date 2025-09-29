package com.pucpr.portplace.features.resource.dtos.resource;

public interface ResourceWithAvailableHoursProjection {
    Long getId();
    String getName();
    String getDescription();
    Integer getDailyHours();
    String getStatus();
    Integer getAvailableHours();
    Long getPositionId();
}