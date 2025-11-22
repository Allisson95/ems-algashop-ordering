package com.github.allisson95.algashop.ordering.domain.model.exception;

import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.ProductId;

public class ShoppingCartItemIncompatibleProductException extends DomainException {

    public ShoppingCartItemIncompatibleProductException(final ProductId actualProductId, final ProductId incompatibleProductId) {
        super("Shopping cart item with product id %s is incompatible with product id %s".formatted(actualProductId, incompatibleProductId));
    }

}
