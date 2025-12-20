package com.github.allisson95.algashop.ordering.domain.model.order;

import com.github.allisson95.algashop.ordering.domain.model.DomainException;

public class OrderInvalidShippingDeliveryDateException extends DomainException {

    public OrderInvalidShippingDeliveryDateException(final OrderId orderId) {
        super("Order %s expected delivery date must be after current date".formatted(orderId));
    }

}
