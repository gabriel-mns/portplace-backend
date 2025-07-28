package com.pucpr.portplace.core.validation.specs;

public interface BiSpecification<A,B> {

    boolean isSatisfiedBy(A candidate, B reference);

}
