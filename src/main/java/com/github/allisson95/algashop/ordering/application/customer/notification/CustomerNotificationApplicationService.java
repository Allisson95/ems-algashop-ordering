package com.github.allisson95.algashop.ordering.application.customer.notification;

import com.github.allisson95.algashop.ordering.domain.model.customer.CustomerId;

public interface CustomerNotificationApplicationService {

    void notifyNewRegistration(CustomerId customerId);

}
