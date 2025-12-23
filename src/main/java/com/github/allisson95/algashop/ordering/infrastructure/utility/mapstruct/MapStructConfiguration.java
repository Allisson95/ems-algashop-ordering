package com.github.allisson95.algashop.ordering.infrastructure.utility.mapstruct;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.SpringMapperConfig;

@MapperConfig(uses = ConversionServiceAdapter.class)
@SpringMapperConfig
public interface MapStructConfiguration {

}
