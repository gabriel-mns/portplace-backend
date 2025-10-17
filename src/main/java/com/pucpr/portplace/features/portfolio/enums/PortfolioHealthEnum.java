package com.pucpr.portplace.features.portfolio.enums;

public enum PortfolioHealthEnum {
    
    RED,
    YELLOW,
    GREEN

    ;

    public String getDisplayName() {
        switch (this) {
            case RED:
                return "Vermelho";
            case YELLOW:
                return "Amarelo";
            case GREEN:
                return "Verde";
            default:
                return this.name();
        }
    }

}
