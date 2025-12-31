package com.github.allisson95.algashop.ordering.application.order.query;

import org.springframework.data.domain.Page;

public interface OrderQueryService {

    OrderDetailOutput findById(String orderId);

    Page<OrderSumaryOutput> filter(OrderFilter filter);

}
