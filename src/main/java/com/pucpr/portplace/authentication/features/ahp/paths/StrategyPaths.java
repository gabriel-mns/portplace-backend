package com.pucpr.portplace.authentication.features.ahp.paths;

public class StrategyPaths {
    
    public static final String STRATEGIES               = "/strategies";
    public static final String STRATEGY_ID              = STRATEGIES + "/{strategyId}";
    public static final String AHPS                     = STRATEGY_ID + "/ahps";
    public static final String AHP_ID                   = AHPS + "/{ahpId}";
    public static final String AHP_RANKING              = AHP_ID + "/ranking";
    public static final String EVALUATIONS              = AHP_ID + "/evaluations";
    public static final String EVALUATION_ID            = EVALUATIONS + "/{evaluationId}";
    public static final String CRITERIA_GROUPS          = STRATEGY_ID + "/criteria-groups";
    public static final String CRITERIA_GROUP_ID        = CRITERIA_GROUPS + "/{criteriaGroupId}";
    public static final String CRITERIA                 = CRITERIA_GROUP_ID + "/criteria";
    public static final String CRITERIA_COMPARISONS     = CRITERIA_GROUP_ID + "/criteria-comparisons";
    public static final String CRITERIA_COMPARISON_ID   = CRITERIA_COMPARISONS + "/{criteriaComparisonId}";

}
