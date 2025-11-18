package com.github.allisson95.algashop.ordering.domain.exception;

import com.github.allisson95.algashop.ordering.domain.entity.OrderStatus;
import com.github.allisson95.algashop.ordering.domain.valueobject.id.OrderId;

public class OrderStatusCannotBeChangedException extends DomainException {

    public OrderStatusCannotBeChangedException(final OrderId id, final OrderStatus status, final OrderStatus newStatus) {
        super("Cannot change order %s status from %s to %s".formatted(id, status, newStatus));
    }

}
