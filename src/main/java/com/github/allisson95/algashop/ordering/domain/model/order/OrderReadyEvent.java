package com.github.allisson95.algashop.ordering.domain.model.order;

import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerId;

import java.time.Instant;

public record OrderReadyEvent(OrderId orderId, CustomerId customerId, Instant readyOn) {

}
