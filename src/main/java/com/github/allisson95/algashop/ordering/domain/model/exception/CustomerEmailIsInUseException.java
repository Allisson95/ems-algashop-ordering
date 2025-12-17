package com.github.allisson95.algashop.ordering.domain.model.exception;

public class CustomerEmailIsInUseException extends DomainException {

    public CustomerEmailIsInUseException() {
        super("Customer email is in use");
    }

}
