package com.github.allisson95.algashop.ordering.domain.model.shoppingcart;

import com.github.allisson95.algashop.ordering.domain.model.DomainException;

public class ShoppingCartCantProceedToCheckoutException extends DomainException {

    public ShoppingCartCantProceedToCheckoutException() {
        super("Shopping cart can't proceed to checkout.");
    }

}
