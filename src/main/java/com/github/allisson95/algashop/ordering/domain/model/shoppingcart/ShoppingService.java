package com.github.allisson95.algashop.ordering.domain.model.shoppingcart;

import com.github.allisson95.algashop.ordering.domain.model.DomainService;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerAlreadyHaveShoppingCartException;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerId;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import com.github.allisson95.algashop.ordering.domain.model.customer.Customers;
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
