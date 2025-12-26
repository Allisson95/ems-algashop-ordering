package com.github.allisson95.algashop.ordering.application.order.management;

import com.github.allisson95.algashop.ordering.domain.model.order.Order;
import com.github.allisson95.algashop.ordering.domain.model.order.OrderId;
import com.github.allisson95.algashop.ordering.domain.model.order.OrderNotFoundException;
import com.github.allisson95.algashop.ordering.domain.model.order.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class OrderManagementApplicationService {

    private final Orders orders;

    @Transactional
    public void cancel(final Long rawOrderId) {
        requireNonNull(rawOrderId, "rawOrderId must not be null");

        final OrderId orderId = new OrderId(rawOrderId);
        final Order order = orders.ofId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.cancel();

        orders.add(order);
    }

    @Transactional
    public void markAsPaid(final Long rawOrderId) {
        requireNonNull(rawOrderId, "rawOrderId must not be null");

        final OrderId orderId = new OrderId(rawOrderId);
        final Order order = orders.ofId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.markAsPaid();

        orders.add(order);
    }

    @Transactional
    public void markAsReady(final Long rawOrderId) {
        requireNonNull(rawOrderId, "rawOrderId must not be null");

        final OrderId orderId = new OrderId(rawOrderId);
        final Order order = orders.ofId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.markAsReady();

        orders.add(order);
    }

}
