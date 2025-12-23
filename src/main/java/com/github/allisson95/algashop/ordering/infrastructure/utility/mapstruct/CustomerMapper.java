package com.github.allisson95.algashop.ordering.infrastructure.utility.mapstruct;

import com.github.allisson95.algashop.ordering.application.customer.management.CustomerOutput;
import com.github.allisson95.algashop.ordering.domain.model.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.extensions.spring.converter.ConversionServiceAdapter;
import org.springframework.core.convert.converter.Converter;

@Mapper(uses = ConversionServiceAdapter.class)
public interface CustomerMapper extends Converter<Customer, CustomerOutput> {

    @Override
    CustomerOutput convert(Customer source);

}
