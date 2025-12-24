package com.github.allisson95.algashop.ordering.application.shoppingcart.management;

import com.github.allisson95.algashop.ordering.DataJpaCleanUpExtension;
import com.github.allisson95.algashop.ordering.domain.model.customer.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ExtendWith(DataJpaCleanUpExtension.class)
class ShoppingCartManagementApplicationServiceIT {

    @Autowired
    private ShoppingCartManagementApplicationService service;

    @Autowired
    private Customers customers;

    @Nested
    class CreateShoppingCart {

        @Test
        void shouldCreateShoppingCart() {
            final Customer customer = CustomerTestDataBuilder.existingCustomer().build();
            customers.add(customer);

            final var shoppingCartId = service.createNew(customer.getId().value());

            assertThat(shoppingCartId).isNotNull();
        }

        @Test
        void shouldThrowExceptionIfTryToCreateShoppingCartWithNonExistingCustomer() {
            assertThatExceptionOfType(CustomerNotFoundException.class)
                    .isThrownBy(() -> service.createNew(new CustomerId().value()));
        }

        @Test
        void shouldThrowExceptionIfTryToCreateShoppingCartWithExistingCartToSameCustomer() {
            final Customer customer = CustomerTestDataBuilder.existingCustomer().build();
            customers.add(customer);
            service.createNew(customer.getId().value());

            assertThatExceptionOfType(CustomerAlreadyHaveShoppingCartException.class)
                    .isThrownBy(() -> service.createNew(customer.getId().value()));
        }

    }

}