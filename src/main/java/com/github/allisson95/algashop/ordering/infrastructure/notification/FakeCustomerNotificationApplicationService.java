package com.github.allisson95.algashop.ordering.infrastructure.notification;

import com.github.allisson95.algashop.ordering.application.customer.notification.CustomerNotificationApplicationService;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerId;
import com.github.allisson95.algashop.ordering.domain.model.customer.Customers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class FakeCustomerNotificationApplicationService implements CustomerNotificationApplicationService {

    private final Customers customers;

    @Override
    public void notifyNewRegistration(final CustomerId customerId) {
        customers.ofId(customerId)
                .ifPresent(customer -> log.info(
                                "Welcome {}!\nUse your email {} to access the system.",
                                customer.getFullName().firstName(),
                                customer.getEmail()
                        )
                );
    }

}
