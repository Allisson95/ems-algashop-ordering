package com.github.allisson95.algashop.ordering.domain.model.order;

import com.github.allisson95.algashop.ordering.domain.model.DomainException;

public class OrderDoesNotContainOrderItemException extends DomainException {

    public OrderDoesNotContainOrderItemException(final OrderId id, final OrderItemId orderItemId) {
        super("Order %s does not contain order item %s".formatted(id, orderItemId));
    }

}
