package com.github.allisson95.algashop.ordering.infrastructure.persistence.assembler;

import com.github.allisson95.algashop.ordering.domain.model.entity.Order;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Address;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Billing;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Shipping;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbeddable;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Component
public class OrderPersistenceEntityAssembler {

    public OrderPersistenceEntity fromDomain(final Order order) {
        return merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(final OrderPersistenceEntity orderPersistenceEntity, final Order order) {
        requireNonNull(orderPersistenceEntity, "orderPersistenceEntity cannot be null");
        requireNonNull(order, "order cannot be null");

        if (isNull(orderPersistenceEntity.getId())) {
            orderPersistenceEntity.setId(order.id().value().toLong());
        }

        orderPersistenceEntity.setCustomerId(order.customerId().value());
        orderPersistenceEntity.setTotalAmount(order.totalAmount().value());
        orderPersistenceEntity.setTotalItems(order.totalItems().value());
        orderPersistenceEntity.setPlacedAt(order.placedAt());
        orderPersistenceEntity.setPaidAt(order.paidAt());
        orderPersistenceEntity.setCancelledAt(order.cancelledAt());
        orderPersistenceEntity.setReadyAt(order.readyAt());
        orderPersistenceEntity.setBilling(assembleBilling(order.billing()));
        orderPersistenceEntity.setShipping(assembleShipping(order.shipping()));
        orderPersistenceEntity.setStatus(order.status().name());
        orderPersistenceEntity.setPaymentMethod(order.paymentMethod().name());
        orderPersistenceEntity.setVersion(order.version());

        return orderPersistenceEntity;
    }

    private BillingEmbeddable assembleBilling(final Billing billing) {
        if (isNull(billing)) {
            return null;
        }

        return BillingEmbeddable.builder()
                .firstName(billing.fullName().firstName())
                .lastName(billing.fullName().lastName())
                .document(billing.document().value())
                .phone(billing.phone().value())
                .email(billing.email().value())
                .address(assembleAddress(billing.address()))
                .build();
    }

    private ShippingEmbeddable assembleShipping(final Shipping shipping) {
        if (isNull(shipping)) {
            return null;
        }

        return ShippingEmbeddable.builder()
                .recipient(assembleRecipient(shipping))
                .address(assembleAddress(shipping.address()))
                .cost(shipping.cost().value())
                .expectedDeliveryDate(shipping.expectedDeliveryDate())
                .build();
    }

    private RecipientEmbeddable assembleRecipient(final Shipping shipping) {
        return RecipientEmbeddable.builder()
                .firstName(shipping.recipient().fullName().firstName())
                .lastName(shipping.recipient().fullName().lastName())
                .document(shipping.recipient().document().value())
                .phone(shipping.recipient().phone().value())
                .build();
    }

    private AddressEmbeddable assembleAddress(final Address address) {
        return AddressEmbeddable.builder()
                .street(address.street())
                .number(address.number())
                .complement(address.complement())
                .neighborhood(address.neighborhood())
                .city(address.city())
                .state(address.state())
                .zipCode(address.zipCode().value())
                .build();
    }

}
