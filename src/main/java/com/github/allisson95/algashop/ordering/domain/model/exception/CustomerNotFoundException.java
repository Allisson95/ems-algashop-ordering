package com.github.allisson95.algashop.ordering.domain.model.exception;

import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.CustomerId;

public class CustomerNotFoundException extends DomainException {

    public CustomerNotFoundException(final CustomerId customerId) {
        super("Customer %s not found".formatted(customerId));
    }

}
