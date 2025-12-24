package com.github.allisson95.algashop.ordering.application.shoppingcart.management;

import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerId;
import com.github.allisson95.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.github.allisson95.algashop.ordering.domain.model.shoppingcart.ShoppingCarts;
import com.github.allisson95.algashop.ordering.domain.model.shoppingcart.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class ShoppingCartManagementApplicationService {

    private final ShoppingService shoppingService;

    private final ShoppingCarts shoppingCarts;

    @Transactional
    public UUID createNew(final UUID rawCustomerId) {
        requireNonNull(rawCustomerId, "customerId cannot be null");

        final var customerId = new CustomerId(rawCustomerId);
        final ShoppingCart shoppingCart = shoppingService.startShopping(customerId);

        shoppingCarts.add(shoppingCart);

        return shoppingCart.getId().value();
    }

}
