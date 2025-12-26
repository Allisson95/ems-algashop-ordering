package com.github.allisson95.algashop.ordering.application.checkout;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CheckoutInput(
        UUID shoppingCartId,
        String paymentMethod,
        ShippingInput shipping,
        BillingData billing
) {

}
