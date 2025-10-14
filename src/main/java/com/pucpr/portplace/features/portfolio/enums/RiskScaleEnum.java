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

    public String getDisplayName() {
        switch (this) {
            case LOW:
                return "Baixo";
            case MEDIUM:
                return "Médio";
            case HIGH:
                return "Alto";
            case VERY_HIGH:
                return "Muito Alto";
            default:
                return this.name();
        }
    }

    
}
