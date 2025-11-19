package com.github.allisson95.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

@Builder(toBuilder = true)
public record Shipping(Recipient recipient, Address address, Money cost, LocalDate expectedDeliveryDate) {

    public Shipping {
        Objects.requireNonNull(recipient, "recipient cannot be null");
        Objects.requireNonNull(address, "address cannot be null");
        Objects.requireNonNull(cost, "cost cannot be null");
        Objects.requireNonNull(expectedDeliveryDate, "expectedDeliveryDate cannot be null");
    }

}
