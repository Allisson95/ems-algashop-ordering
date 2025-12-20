package com.github.allisson95.algashop.ordering.domain.model.order;

import com.github.allisson95.algashop.ordering.domain.model.DomainException;

public class OrderNotBelongsToCustomerException extends DomainException {

    public OrderNotBelongsToCustomerException() {
        super("Order not belongs to customer");
    }

}
