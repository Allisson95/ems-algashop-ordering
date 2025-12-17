package com.github.allisson95.algashop.ordering.domain.model.exception;

public class OrderNotBelongsToCustomerException extends DomainException {

    public OrderNotBelongsToCustomerException() {
        super("Order not belongs to customer");
    }

}
