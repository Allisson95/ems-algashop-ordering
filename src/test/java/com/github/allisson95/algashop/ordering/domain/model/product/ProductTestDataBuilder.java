package com.github.allisson95.algashop.ordering.domain.model.product;

import com.github.allisson95.algashop.ordering.domain.model.commons.Money;
import net.datafaker.Faker;

public class ProductTestDataBuilder {

    private static final Faker faker = new Faker();

    private ProductTestDataBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .id(new ProductId())
                .name(new ProductName(faker.commerce().productName()))
                .price(new Money(faker.commerce().price()))
                .inStock(true);
    }

    public static Product.ProductBuilder anOutOfStockProduct() {
        return Product.builder()
                .id(new ProductId())
                .name(new ProductName(faker.commerce().productName()))
                .price(new Money(faker.commerce().price()))
                .inStock(false);
    }

}
