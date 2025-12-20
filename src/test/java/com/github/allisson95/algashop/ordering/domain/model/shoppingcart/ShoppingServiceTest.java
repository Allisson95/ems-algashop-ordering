package com.github.allisson95.algashop.ordering.domain.model.shoppingcart;

import com.github.allisson95.algashop.ordering.domain.model.commons.Money;
import com.github.allisson95.algashop.ordering.domain.model.commons.Quantity;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerAlreadyHaveShoppingCartException;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerId;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import com.github.allisson95.algashop.ordering.domain.model.customer.Customers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingServiceTest {

    @Mock
    private Customers customers;

    @Mock
    private ShoppingCarts shoppingCarts;

    @InjectMocks
    private ShoppingService service;

    @Test
    void shouldStartShopping() {
        final CustomerId customerId = new CustomerId();

        when(customers.exists(customerId)).thenReturn(true);
        when(shoppingCarts.existsByCustomer(customerId)).thenReturn(false);

        final ShoppingCart shoppingCart = service.startShopping(customerId);

        assertThat(shoppingCart).isNotNull();
        assertThat(shoppingCart.customerId()).isEqualTo(customerId);
        assertThat(shoppingCart.isEmpty()).isTrue();
        assertThat(shoppingCart.totalAmount()).isEqualTo(Money.ZERO);
        assertThat(shoppingCart.totalItems()).isEqualTo(Quantity.ZERO);

        verify(customers).exists(customerId);
        verify(shoppingCarts).existsByCustomer(customerId);
    }

    @Test
    void shouldThrowExceptionIfCustomerDoesNotExists() {
        final CustomerId customerId = new CustomerId();

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> service.startShopping(customerId));

        verify(customers).exists(customerId);
        verify(shoppingCarts, never()).existsByCustomer(customerId);
    }

    @Test
    void shouldThrowAnExceptionIfACartAlreadyExistsForTheGivenCustomer() {
        final CustomerId customerId = new CustomerId();

        when(customers.exists(customerId)).thenReturn(true);
        when(shoppingCarts.existsByCustomer(customerId)).thenReturn(true);

        assertThatExceptionOfType(CustomerAlreadyHaveShoppingCartException.class)
                .isThrownBy(() -> service.startShopping(customerId));

        verify(customers).exists(customerId);
        verify(shoppingCarts).existsByCustomer(customerId);
    }

}