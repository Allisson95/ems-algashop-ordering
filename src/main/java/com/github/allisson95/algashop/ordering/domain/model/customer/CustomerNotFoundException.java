package com.github.allisson95.algashop.ordering.domain.model.customer;

import com.github.allisson95.algashop.ordering.domain.model.DomainException;

public class CustomerNotFoundException extends DomainException {

    public CustomerNotFoundException(final CustomerId customerId) {
        super("Customer %s not found".formatted(customerId));
    }

}
