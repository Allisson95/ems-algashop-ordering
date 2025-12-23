package com.github.allisson95.algashop.ordering.application.customer.management;

import com.github.allisson95.algashop.ordering.DataJpaCleanUpExtension;
import com.github.allisson95.algashop.ordering.application.commons.AddressData;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerArchivedException;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerId;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(DataJpaCleanUpExtension.class)
class CustomerManagementApplicationServiceIT {

    private static final Faker faker = new Faker();

    @Autowired
    private CustomerManagementApplicationService service;

    @Test
    void shouldCreate() {
        final CustomerInput customerInput = CustomerInput.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .birthDate(faker.timeAndDate().birthday())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().cellPhone())
                .document(faker.passport().valid())
                .promotionNotificationsAllowed(faker.bool().bool())
                .address(AddressData.builder()
                        .street(faker.address().streetAddress())
                        .number(faker.address().buildingNumber())
                        .complement(faker.address().secondaryAddress())
                        .neighborhood(faker.address().secondaryAddress())
                        .city(faker.address().city())
                        .state(faker.address().state())
                        .zipCode(faker.address().zipCode())
                        .build())
                .build();

        final UUID customerId = service.create(customerInput);

        assertThat(customerId).isNotNull();

        final CustomerOutput customerOutput = service.findById(customerId);

        assertWith(customerOutput,
                c -> assertThat(c).isNotNull(),
                c -> assertThat(c.id()).isEqualTo(customerId),
                c -> assertThat(c.firstName()).isEqualTo(customerInput.firstName()),
                c -> assertThat(c.lastName()).isEqualTo(customerInput.lastName()),
                c -> assertThat(c.birthDate()).isEqualTo(customerInput.birthDate()),
                c -> assertThat(c.email()).isEqualTo(customerInput.email()),
                c -> assertThat(c.phone()).isEqualTo(customerInput.phone()),
                c -> assertThat(c.document()).isEqualTo(customerInput.document()),
                c -> assertThat(c.promotionNotificationsAllowed()).isEqualTo(customerInput.promotionNotificationsAllowed()),
                c -> assertThat(c.loyaltyPoints()).isEqualTo(0),
                c -> assertThat(c.archived()).isFalse(),
                c -> assertThat(c.archivedAt()).isNull(),
                c -> assertThat(c.registeredAt()).isNotNull(),
                c -> assertThat(c.address()).isEqualTo(customerInput.address())
        );
    }

    @Test
    void shouldUpdate() {
        final CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        final CustomerUpdateInput updateInput = CustomerUpdateInputTestDataBuilder.aCustomerUpdate().build();

        final UUID customerId = service.create(customerInput);
        assertThat(customerId).isNotNull();

        service.update(customerId, updateInput);

        final CustomerOutput customerOutput = service.findById(customerId);

        assertWith(customerOutput,
                c -> assertThat(c).isNotNull(),
                c -> assertThat(c.id()).isEqualTo(customerId),
                c -> assertThat(c.firstName()).isEqualTo(updateInput.firstName()),
                c -> assertThat(c.lastName()).isEqualTo(updateInput.lastName()),
                c -> assertThat(c.birthDate()).isEqualTo(customerInput.birthDate()),
                c -> assertThat(c.email()).isEqualTo(customerInput.email()),
                c -> assertThat(c.phone()).isEqualTo(updateInput.phone()),
                c -> assertThat(c.document()).isEqualTo(customerInput.document()),
                c -> assertThat(c.promotionNotificationsAllowed()).isEqualTo(updateInput.promotionNotificationsAllowed()),
                c -> assertThat(c.loyaltyPoints()).isEqualTo(0),
                c -> assertThat(c.archived()).isFalse(),
                c -> assertThat(c.archivedAt()).isNull(),
                c -> assertThat(c.registeredAt()).isNotNull(),
                c -> assertThat(c.address()).isEqualTo(updateInput.address())
        );
    }

    @Test
    void shouldArchive() {
        final CustomerInput customerInput = CustomerInput.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .birthDate(faker.timeAndDate().birthday())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().cellPhone())
                .document(faker.passport().valid())
                .promotionNotificationsAllowed(faker.bool().bool())
                .address(AddressData.builder()
                        .street(faker.address().streetAddress())
                        .number(faker.address().buildingNumber())
                        .complement(faker.address().secondaryAddress())
                        .neighborhood(faker.address().secondaryAddress())
                        .city(faker.address().city())
                        .state(faker.address().state())
                        .zipCode(faker.address().zipCode())
                        .build())
                .build();

        final UUID customerId = service.create(customerInput);

        assertThat(customerId).isNotNull();

        service.archive(customerId);

        final CustomerOutput customerOutput = service.findById(customerId);

        assertWith(customerOutput,
                c -> assertThat(c).isNotNull(),
                c -> assertThat(c.id()).isEqualTo(customerId),
                c -> assertThat(c.archived()).isTrue(),
                c -> assertThat(c.archivedAt()).isNotNull(),
                c -> assertThat(c.archivedAt()).isCloseTo(Instant.now(), within(1, ChronoUnit.SECONDS))
        );
    }

    @Test
    void shouldThrowExceptionIfArchiveCustomerThatDoesNotExist() {
        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> service.archive(new CustomerId().value()));
    }

    @Test
    void shouldThrowExceptionIfArchiveCustomerThatIsAlreadyArchived() {
        final CustomerInput customerInput = CustomerInput.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .birthDate(faker.timeAndDate().birthday())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().cellPhone())
                .document(faker.passport().valid())
                .promotionNotificationsAllowed(faker.bool().bool())
                .address(AddressData.builder()
                        .street(faker.address().streetAddress())
                        .number(faker.address().buildingNumber())
                        .complement(faker.address().secondaryAddress())
                        .neighborhood(faker.address().secondaryAddress())
                        .city(faker.address().city())
                        .state(faker.address().state())
                        .zipCode(faker.address().zipCode())
                        .build())
                .build();

        final UUID customerId = service.create(customerInput);

        assertThat(customerId).isNotNull();

        service.archive(customerId);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> service.archive(customerId));
    }

}