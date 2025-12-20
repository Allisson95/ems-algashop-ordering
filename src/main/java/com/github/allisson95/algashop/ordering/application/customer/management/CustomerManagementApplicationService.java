package com.github.allisson95.algashop.ordering.application.customer.management;

import com.github.allisson95.algashop.ordering.application.commons.AddressData;
import com.github.allisson95.algashop.ordering.domain.model.commons.*;
import com.github.allisson95.algashop.ordering.domain.model.customer.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
class CustomerManagementApplicationService {

    private final CustomerRegistrationService registrationService;

    private final Customers customers;

    @Transactional
    public UUID create(final CustomerInput input) {
        requireNonNull(input, "input cannot be null");

        final Customer registeredCustomer = registrationService.register(
                new FullName(input.firstName(), input.lastName()),
                new BirthDate(input.birthDate()),
                new Email(input.email()),
                new Phone(input.phone()),
                new Document(input.document()),
                input.promotionNotificationsAllowed(),
                Address.builder()
                        .street(input.address().street())
                        .number(input.address().number())
                        .complement(input.address().complement())
                        .neighborhood(input.address().neighborhood())
                        .city(input.address().city())
                        .state(input.address().state())
                        .zipCode(new ZipCode(input.address().zipCode()))
                        .build()
        );

        customers.add(registeredCustomer);

        return registeredCustomer.id().value();
    }

    @Transactional(readOnly = true)
    public CustomerOutput findById(final UUID customerId) {
        requireNonNull(customerId, "customerId cannot be null");
        final CustomerId customerIdentifier = new CustomerId(customerId);
        final Customer customer = customers.ofId(customerIdentifier)
                .orElseThrow(() -> new CustomerNotFoundException(customerIdentifier));

        return CustomerOutput.builder()
                .id(customer.id().value())
                .firstName(customer.fullName().firstName())
                .lastName(customer.fullName().lastName())
                .birthDate(ofNullable(customer.birthDate()).map(BirthDate::value).orElse(null))
                .email(customer.email().value())
                .phone(customer.phone().value())
                .document(customer.document().value())
                .promotionNotificationsAllowed(customer.isPromotionNotificationsAllowed())
                .loyaltyPoints(customer.loyaltyPoints().value())
                .registeredAt(customer.registeredAt())
                .archived(customer.isArchived())
                .archivedAt(customer.archivedAt())
                .address(AddressData.builder()
                        .street(customer.address().street())
                        .number(customer.address().number())
                        .complement(customer.address().complement())
                        .neighborhood(customer.address().neighborhood())
                        .city(customer.address().city())
                        .state(customer.address().state())
                        .zipCode(customer.address().zipCode().value())
                        .build())
                .build();
    }

}
