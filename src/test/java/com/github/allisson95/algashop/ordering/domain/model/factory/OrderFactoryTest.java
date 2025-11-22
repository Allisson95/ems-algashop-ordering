package com.github.allisson95.algashop.ordering.domain.model.factory;

import com.github.allisson95.algashop.ordering.domain.model.entity.Order;
import com.github.allisson95.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.github.allisson95.algashop.ordering.domain.model.entity.PaymentMethod;
import com.github.allisson95.algashop.ordering.domain.model.entity.ProductTestDataBuilder;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Billing;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Product;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Quantity;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Shipping;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.CustomerId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;

class OrderFactoryTest {

    @Test
    void shouldGenerateFilledOrder() {
        final CustomerId customerId = new CustomerId();
        final Billing billing = OrderTestDataBuilder.aBilling();
        final Shipping shipping = OrderTestDataBuilder.aShipping();
        final PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;
        final Product product = ProductTestDataBuilder.aProduct().build();
        final Quantity productQuantity = new Quantity(1);

        final Order filledOrder = OrderFactory.filled(customerId, billing, shipping, paymentMethod, product, productQuantity);

        assertWith(filledOrder,
                o -> assertThat(o.customerId()).isEqualTo(customerId),
                o -> assertThat(o.billing()).isEqualTo(billing),
                o -> assertThat(o.shipping()).isEqualTo(shipping),
                o -> assertThat(o.paymentMethod()).isEqualTo(paymentMethod),
                o -> assertThat(o.totalItems()).isEqualTo(new Quantity(1)),
                o -> assertThat(o.isDraft()).isTrue()
        );
    }

}