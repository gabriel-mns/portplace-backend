package com.pucpr.portplace.features.ahp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImportanceScale {
    
    EXTREMELY_MORE_IMPORTANT(9.0, 1.0 / 9.0),
    MUCH_MORE_IMPORTANT(6.0, 1.0 / 6.0),
    MORE_IMPORTANT(3.0, 1.0 / 3.0),
    EQUALLY_IMPORTANT(1.0, 1.0),
    LESS_IMPORTANT(1.0 / 3.0, 3.0),
    MUCH_LESS_IMPORTANT(1.0 / 6.0, 6.0),
    EXTREMELY_LESS_IMPORTANT(1.0 / 9.0, 9.0);

    private final double value;
    private final double reciprocal;
    
}
