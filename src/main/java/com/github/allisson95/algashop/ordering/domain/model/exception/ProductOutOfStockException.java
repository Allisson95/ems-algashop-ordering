package com.github.allisson95.algashop.ordering.domain.model.exception;

import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.ProductId;

public class ProductOutOfStockException extends DomainException {

    public ProductOutOfStockException(final ProductId productId) {
        super("Product %s out of stock".formatted(productId));
    }

}
