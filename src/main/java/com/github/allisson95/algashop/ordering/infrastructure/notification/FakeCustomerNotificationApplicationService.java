package com.github.allisson95.algashop.ordering.infrastructure.notification;

import com.github.allisson95.algashop.ordering.application.customer.notification.CustomerNotificationApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class FakeCustomerNotificationApplicationService implements CustomerNotificationApplicationService {

    @Override
    public void notifyNewRegistration(final NotifyNewRegistrationInput input) {
        log.info(
                "Welcome {}!\nUse your email {} to access the system.",
                input.firstName(),
                input.email()
        );
    }

}
