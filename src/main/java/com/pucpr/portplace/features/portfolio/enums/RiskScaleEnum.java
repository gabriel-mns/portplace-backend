package com.pucpr.portplace.features.portfolio.enums;

public enum RiskScaleEnum {

    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4);

    private final int value;

    RiskScaleEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
}
