package com.github.allisson95.algashop.ordering.domain.model.customer;

import com.github.allisson95.algashop.ordering.domain.model.DomainException;

public class CustomerAlreadyHaveShoppingCartException extends DomainException {

    public CustomerAlreadyHaveShoppingCartException(final CustomerId customerId) {
        super("Customer %s already have a shopping cart".formatted(customerId));
    }

}
