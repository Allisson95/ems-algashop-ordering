package com.github.allisson95.algashop.ordering.application.customer.management;

import com.github.allisson95.algashop.ordering.application.utility.Mapper;
import com.github.allisson95.algashop.ordering.domain.model.commons.*;
import com.github.allisson95.algashop.ordering.domain.model.customer.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
class CustomerManagementApplicationService {

    private final CustomerRegistrationService registrationService;

    private final Customers customers;

    private final Mapper mapper;

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

        return registeredCustomer.getId().value();
    }

    @Transactional(readOnly = true)
    public CustomerOutput findById(final UUID customerId) {
        requireNonNull(customerId, "customerId cannot be null");
        final CustomerId customerIdentifier = new CustomerId(customerId);
        return customers.ofId(customerIdentifier)
                .map(customer -> mapper.convert(customer, CustomerOutput.class))
                .orElseThrow(() -> new CustomerNotFoundException(customerIdentifier));
    }

}
