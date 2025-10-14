package com.pucpr.portplace.features.portfolio.enums;

public enum PortfolioStatusEnum {
    
    EMPTY,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED

    ;

    public String getDisplayName() {
        switch (this) {
            case EMPTY:
                return "Vazio";
            case IN_PROGRESS:
                return "Em andamento";
            case COMPLETED:
                return "Concluído";
            case CANCELLED:
                return "Cancelado";
            default:
                return this.name();
        }
    }

}
