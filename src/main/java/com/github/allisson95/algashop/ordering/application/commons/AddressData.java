package com.github.allisson95.algashop.ordering.application.commons;

import lombok.Builder;

@Builder
public record AddressData(
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode
) {

}
