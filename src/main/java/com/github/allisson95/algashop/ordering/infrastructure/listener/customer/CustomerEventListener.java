package com.github.allisson95.algashop.ordering.infrastructure.listener.customer;

import com.github.allisson95.algashop.ordering.application.customer.notification.CustomerNotificationApplicationService;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerArchivedEvent;
import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerEventListener {

    private final CustomerNotificationApplicationService customerNotificationApplicationService;

    @EventListener
    public void handleCustomerCreatedEvent(final CustomerRegisteredEvent event) {
        log.info("Handling customer registered event: {}", event);
        customerNotificationApplicationService.notifyNewRegistration(event.customerId());
    }

    @EventListener
    public void handleCustomerArchivedEvent(final CustomerArchivedEvent event) {
        log.info("Handling customer archived event: {}", event);
    }

}
