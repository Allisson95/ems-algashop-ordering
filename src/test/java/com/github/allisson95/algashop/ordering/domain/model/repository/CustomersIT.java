package com.github.allisson95.algashop.ordering.domain.model.repository;

import com.github.allisson95.algashop.ordering.domain.model.entity.Customer;
import com.github.allisson95.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.FullName;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.configuration.SpringDataJpaConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Import(SpringDataJpaConfiguration.class)
@DataJpaTest(
        showSql = false,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Persistence(Provider|EntityAssembler|EntityDisassembler)"),
        }
)
class CustomersIT {

    @Autowired
    private Customers customers;

    @Test
    void shouldPersistAndFind() {
        final Customer newCustomer = CustomerTestDataBuilder.newCustomer().build();
        customers.add(newCustomer);

        final Optional<Customer> possibleCustomer = customers.ofId(newCustomer.id());

        assertThat(possibleCustomer).isPresent();
        assertWith(possibleCustomer.get(),
                c -> assertThat(c.id()).isEqualTo(newCustomer.id()));
    }

    @Test
    void shouldUpdateExistingCustomer() {
        Customer customer = CustomerTestDataBuilder.newCustomer().build();
        customers.add(customer);

        customer = customers.ofId(customer.id()).orElseThrow();
        assertThat(customer.isArchived()).isFalse();
        assertThat(customer.archivedAt()).isNull();

        customer.archive();

        customers.add(customer);

        customer = customers.ofId(customer.id()).orElseThrow();
        assertThat(customer.isArchived()).isTrue();
        assertThat(customer.archivedAt()).isNotNull();
    }

    @Test
    void shouldNotAllowStaleUpdates() {
        Customer customer = CustomerTestDataBuilder.newCustomer().build();
        customers.add(customer);

        Customer customer1 = customers.ofId(customer.id()).orElseThrow();
        Customer customer2 = customers.ofId(customer.id()).orElseThrow();

        customer1.archive();
        customers.add(customer1);

        customer2.changeName(new FullName("John", "Doe"));

        assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                .isThrownBy(() -> customers.add(customer2));

        Customer savedCustomer = customers.ofId(customer.id()).orElseThrow();

        assertThat(savedCustomer.isArchived()).isTrue();
        assertThat(savedCustomer.archivedAt()).isNotNull();
        assertThat(savedCustomer.fullName()).isNotEqualTo(new FullName("John", "Doe"));
    }

    @Test
    void shouldCountExistingCustomers() {
        assertThat(customers.count()).isZero();

        final Customer customer = CustomerTestDataBuilder.newCustomer().build();
        customers.add(customer);

        assertThat(customers.count()).isEqualTo(1);
    }

    @Test
    void shouldReturnIfCustomerExists() {
        final Customer customer = CustomerTestDataBuilder.newCustomer().build();
        customers.add(customer);

        assertThat(customers.exists(customer.id())).isTrue();
        assertThat(customers.exists(new CustomerId())).isFalse();
    }

}
