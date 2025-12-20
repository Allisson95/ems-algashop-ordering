package com.github.allisson95.algashop.ordering.domain.model.shoppingcart;

import com.github.allisson95.algashop.ordering.domain.model.RemoveCapableRepository;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerId;

import java.util.Optional;

public interface ShoppingCarts extends RemoveCapableRepository<ShoppingCart, ShoppingCartId> {

    Optional<ShoppingCart> ofCustomer(CustomerId customerId);

    boolean existsByCustomer(CustomerId customerId);

}
