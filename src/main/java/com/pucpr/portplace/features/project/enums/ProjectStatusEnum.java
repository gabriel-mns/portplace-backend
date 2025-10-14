package com.pucpr.portplace.features.project.enums;

public enum ProjectStatusEnum {
    
    IN_ANALYSIS,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
    ;

    public String getDisplayName() {
        switch (this) {
            case IN_ANALYSIS:
                return "Em análise";
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
