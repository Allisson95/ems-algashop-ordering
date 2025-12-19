package com.github.allisson95.algashop.ordering.domain.model.service;

import com.github.allisson95.algashop.ordering.domain.model.entity.ShoppingCart;
import com.github.allisson95.algashop.ordering.domain.model.exception.CustomerAlreadyHaveShoppingCartException;
import com.github.allisson95.algashop.ordering.domain.model.exception.CustomerNotFoundException;
import com.github.allisson95.algashop.ordering.domain.model.repository.Customers;
import com.github.allisson95.algashop.ordering.domain.model.repository.ShoppingCarts;
import com.github.allisson95.algashop.ordering.domain.model.utility.DomainService;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.CustomerId;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.requireNonNull;

@DomainService
@RequiredArgsConstructor
public class ShoppingService {

    private final Customers customers;

    private final ShoppingCarts shoppingCarts;

    public ShoppingCart startShopping(final CustomerId customerId) {
        requireNonNull(customerId, "customerId cannot be null");

        verifyIfCustomerExists(customerId);
        verifyIfExistsShoppingCartForCustomer(customerId);

        return ShoppingCart.startShopping(customerId);
    }

    private void verifyIfCustomerExists(final CustomerId customerId) {
        if (!customers.exists(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
    }

    private void verifyIfExistsShoppingCartForCustomer(final CustomerId customerId) {
        if (shoppingCarts.existsByCustomer(customerId)) {
            throw new CustomerAlreadyHaveShoppingCartException(customerId);
        }
    }

}
