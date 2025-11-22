package com.github.allisson95.algashop.ordering.domain.model.entity;

import com.github.allisson95.algashop.ordering.domain.model.exception.OrderCannotBeEditedException;
import com.github.allisson95.algashop.ordering.domain.model.exception.OrderDoesNotContainOrderItemException;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Money;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Quantity;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.MathContext;

import static org.assertj.core.api.Assertions.*;

class OrderRemoveItemTest {

    @Test
    void givenADraftOrderWithItems_whenRemoveItem_shouldBeOk() {
        final Order order = OrderTestDataBuilder.anOrder().build();
        final OrderItem orderItem = order.items().iterator().next();
        final Quantity expectedTotalItems = new Quantity(order.totalItems().value() - orderItem.quantity().value());
        final Money expectedTotalAmount = new Money(order.totalAmount().value().subtract(orderItem.totalAmount().value(), MathContext.DECIMAL32));

        assertWith(order,
                o -> assertThatCode(() -> o.removeItem(orderItem.id())).doesNotThrowAnyException(),
                o -> assertThat(o.items()).doesNotContain(orderItem),
                o -> assertThat(o.totalItems()).isEqualTo(expectedTotalItems),
                o -> assertThat(o.totalAmount()).isEqualTo(expectedTotalAmount)
        );
    }

    @Test
    void givenADraftOrderWithItems_whenTryToRemoveNonexistentItem_shouldThrowException() {
        final Order order = OrderTestDataBuilder.anOrder().build();
        final OrderItemId orderItemId = new OrderItemId();

        assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class)
                .isThrownBy(() -> order.removeItem(orderItemId))
                .withMessage("Order %s does not contain order item %s".formatted(order.id(), orderItemId));
    }

    @ParameterizedTest
    @EnumSource(names = { "DRAFT" }, mode = EnumSource.Mode.EXCLUDE)
    void givenANonDraftOrder_whenTryToRemoveItem_shouldThrowException(final OrderStatus nonDraftStatus) {
        final Order order = OrderTestDataBuilder.anOrder().status(nonDraftStatus).build();
        final OrderItemId orderItemId = order.items().iterator().next().id();

        assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.removeItem(orderItemId))
                .withMessage("Order %s with status %s cannot be edited".formatted(order.id(), order.status()));
    }

}
