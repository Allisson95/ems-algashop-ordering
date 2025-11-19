package com.github.allisson95.algashop.ordering.domain.entity;

import com.github.allisson95.algashop.ordering.domain.valueobject.Money;
import com.github.allisson95.algashop.ordering.domain.valueobject.ProductName;
import com.github.allisson95.algashop.ordering.domain.valueobject.Quantity;
import com.github.allisson95.algashop.ordering.domain.valueobject.id.OrderId;
import com.github.allisson95.algashop.ordering.domain.valueobject.id.ProductId;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    Faker faker = new Faker();

    @Test
    void shouldGenerateOrderItem() {
        final OrderItem orderItem = OrderItem.newOrderItem()
                .orderId(new OrderId())
                .productId(new ProductId())
                .productName(new ProductName(faker.commerce().productName()))
                .price(new Money(faker.commerce().price()))
                .quantity(new Quantity(faker.number().numberBetween(1, 10)))
                .build();
        assertThat(orderItem).isNotNull();
    }

}