package com.tmax.commerce.itemmodule.config.mapstruct;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public class mapperConfig {
}
