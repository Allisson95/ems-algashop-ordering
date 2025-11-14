package com.github.allisson95.algashop.ordering.domain.entity;

import com.github.allisson95.algashop.ordering.domain.exception.CustomerArchivedException;
import com.github.allisson95.algashop.ordering.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldThrowException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Customer(
                        new FullName("John", "Doe"),
                        new BirthDate(LocalDate.of(1991, 7, 5)),
                        new Email("invalid"),
                        new Phone("478-256-2504"),
                        new Document("255-08-0578"),
                        true,
                        Instant.now()
                ))
                .withMessage("invalid is not a well-formed email address");
    }

    @Test
    void given_invalidEmail_whenTryUpdateCustomerEmail_shouldThrowException() {
        final Customer customer = new Customer(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("johndoe@email.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                true,
                Instant.now()
        );
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("invalid")))
                .withMessage("invalid is not a well-formed email address");
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
        final Customer customer = new Customer(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("johndoe@email.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                true,
                Instant.now()
        );

        customer.archive();

        assertWith(customer,
                c -> assertThat(c.isArchived()).isTrue(),
                c -> assertThat(c.archivedAt()).isNotNull(),
                c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous")),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.email()).isNotEqualTo(new Email("johndoe@email.com")),
                c -> assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
                c -> assertThat(c.document()).isEqualTo(new Document("000-00-0000")),
                c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse()
        );
    }

    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldThrowException() {
        final Customer customer = new Customer(
                new CustomerId(),
                new FullName("Anonymous", "Anonymous"),
                null,
                new Email("anonymous@email.com"),
                new Phone("000-000-0000"),
                new Document("000-00-0000"),
                true,
                true,
                Instant.now(),
                Instant.now(),
                LoyaltyPoints.ZERO
        );

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(10)))
                .withMessage("Customer is archived and cannot be updated");

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive)
                .withMessage("Customer is archived and cannot be updated");

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications)
                .withMessage("Customer is archived and cannot be updated");

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::disablePromotionNotifications)
                .withMessage("Customer is archived and cannot be updated");

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeName(new FullName("New", "Name")))
                .withMessage("Customer is archived and cannot be updated");

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("johndoe@email.com")))
                .withMessage("Customer is archived and cannot be updated");

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone(new Phone("New Phone")))
                .withMessage("Customer is archived and cannot be updated");
    }

    @Test
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {
        final Customer customer = new Customer(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("johndoe@email.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                true,
                Instant.now()
        );

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

        assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldThrowException() {
        final Customer customer = new Customer(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("johndoe@email.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                true,
                Instant.now()
        );

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(0)))
                .withMessage("loyaltyPointsToAdd cannot be negative or zero");
    }

}