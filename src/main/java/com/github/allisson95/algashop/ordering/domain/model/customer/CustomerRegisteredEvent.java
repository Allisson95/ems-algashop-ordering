package com.github.allisson95.algashop.ordering.domain.model.customer;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

public record CustomerRegisteredEvent(CustomerId customerId, Instant registeredOn) {

    public CustomerRegisteredEvent {
        requireNonNull(customerId, "customerId cannot be null");
        requireNonNull(registeredOn, "registeredOn cannot be null");
    }

}
