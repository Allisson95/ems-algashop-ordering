package com.github.allisson95.algashop.ordering.domain.model.customer;

import com.github.allisson95.algashop.ordering.domain.model.DomainException;

public class CustomerEmailIsInUseException extends DomainException {

    public CustomerEmailIsInUseException() {
        super("Customer email is in use");
    }

}
