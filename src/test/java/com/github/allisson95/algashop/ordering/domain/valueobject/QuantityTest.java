package com.github.allisson95.algashop.ordering.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class QuantityTest {

    @Test
    void shouldCreateQuantity() {
        final Quantity quantity = new Quantity(5);
        assertWith(quantity,
                q -> assertThat(quantity.value()).isEqualTo(5),
                q -> assertThat(quantity.toString()).isEqualTo("5"));
    }

    @Test
    void shouldThrowsExceptionWhenTryToCreateQuantityWithNullValueOrNegativeValue() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Quantity(null))
                .withMessage("quantity cannot be null");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Quantity(-1))
                .withMessage("quantity cannot be negative");
    }

    @Test
    void shouldReturnANewQuantityWithAddedValue() {
        final Quantity q1 = new Quantity(3);
        final Quantity q2 = new Quantity(5);

        final Quantity result = q1.add(q2);

        assertThat(result).isEqualTo(new Quantity(8));
    }

    @Test
    void shouldThrowsExceptionWhenTryToAddNullQuantity() {
        final Quantity q1 = new Quantity(3);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> q1.add(null))
                .withMessage("quantityToAdd cannot be null");
    }

}
