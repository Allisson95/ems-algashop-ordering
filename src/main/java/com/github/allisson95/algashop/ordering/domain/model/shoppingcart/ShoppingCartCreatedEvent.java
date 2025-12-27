package com.github.allisson95.algashop.ordering.domain.model.shoppingcart;

import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerId;

import java.time.Instant;

public record ShoppingCartCreatedEvent(ShoppingCartId shoppingCartId, CustomerId customerId, Instant createdOn) {

}
