package com.github.allisson95.algashop.ordering.infrastructure.persistence.assembler;

import com.github.allisson95.algashop.ordering.domain.model.entity.Order;
import com.github.allisson95.algashop.ordering.domain.model.entity.OrderItem;
import com.github.allisson95.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;

import static org.assertj.core.api.Assertions.*;

class OrderPersistenceEntityAssemblerTest {

    private final OrderPersistenceEntityAssembler assembler = new OrderPersistenceEntityAssembler();

    @Test
    void fromDomain() {
        final Order order = OrderTestDataBuilder.anOrder().build();

        final OrderPersistenceEntity entity = assembler.fromDomain(order);

        assertWith(entity,
                e -> assertThat(e.getId()).isEqualTo(order.id().value().toLong()),
                e -> assertThat(e.getCustomerId()).isEqualTo(order.customerId().value()),
                e -> assertThat(e.getTotalAmount()).isEqualTo(order.totalAmount().value()),
                e -> assertThat(e.getTotalItems()).isEqualTo(order.totalItems().value()),
                e -> assertThat(e.getPlacedAt()).isEqualTo(order.placedAt()),
                e -> assertThat(e.getPaidAt()).isEqualTo(order.paidAt()),
                e -> assertThat(e.getCancelledAt()).isEqualTo(order.cancelledAt()),
                e -> assertThat(e.getReadyAt()).isEqualTo(order.readyAt()),
                e -> assertThat(e.getStatus()).isEqualTo(order.status().name()),
                e -> assertThat(e.getPaymentMethod()).isEqualTo(order.paymentMethod().name())
        );
    }

    @Test
    void givenOrderWithoutItems_whenMerge_shouldRemovePersistenceEntityItems() {
        final Order order = OrderTestDataBuilder.anOrder().withItems(false).build();
        final OrderPersistenceEntity existingOrderEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();

        assertThat(order.items()).isEmpty();
        assertThat(existingOrderEntity.getItems()).isNotEmpty();

        assembler.merge(existingOrderEntity, order);

        assertThat(existingOrderEntity.getItems()).isEmpty();
    }

    @Test
    void givenOrderWithItems_whenMerge_shouldAddItemsToPersistenceEntity() {
        final Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        final OrderPersistenceEntity existingOrderEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().items(new LinkedHashSet<>()).build();

        assertThat(order.items()).isNotEmpty();
        assertThat(existingOrderEntity.getItems()).isEmpty();

        assembler.merge(existingOrderEntity, order);

        assertThat(existingOrderEntity.getItems()).isNotEmpty();
        assertThat(existingOrderEntity.getItems()).hasSize(order.items().size());
    }

    @Test
    void givenOrderWithItems_whenMerge_shouldUpdateItemsInPersistenceEntity() {
        final Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        final OrderPersistenceEntity existingOrderEntity = assembler.fromDomain(order);
        final OrderItem itemToRemove = order.items().iterator().next();
        order.removeItem(itemToRemove.id());

        assertThat(order.items()).hasSize(2);
        assertThat(existingOrderEntity.getItems()).hasSize(3);

        assembler.merge(existingOrderEntity, order);

        assertWith(existingOrderEntity.getItems(),
                i -> assertThat(i).hasSize(2),
                i -> assertThatCollection(i).doesNotContain(assembler.fromDomain(itemToRemove))
        );
    }

}