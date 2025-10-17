package com.pucpr.portplace.features.resource.dtos.resource;

public interface ResourceWithAvailableHoursProjection {
    Long getId();
    String getName();
    String getDescription();
    Integer getDailyHours();
    String getStatus();
    Integer getAvailableHours();
    Integer getRelatedProjectsCount();
    Long getPositionId();
    String getCreatedBy();
    String getLastModifiedBy();
    String getCreatedAt();
    String getLastModifiedAt();
    Boolean getDisabled();
}