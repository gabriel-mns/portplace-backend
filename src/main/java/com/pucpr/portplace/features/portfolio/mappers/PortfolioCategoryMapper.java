package com.pucpr.portplace.features.portfolio.mappers;

import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {PortfolioMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
    )
public interface PortfolioCategoryMapper {

}
