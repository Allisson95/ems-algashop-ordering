package com.github.allisson95.algashop.ordering.domain.model.order;

import com.github.allisson95.algashop.ordering.domain.model.Specification;
import com.github.allisson95.algashop.ordering.domain.model.customer.Customer;
import com.github.allisson95.algashop.ordering.domain.model.customer.LoyaltyPoints;
import lombok.RequiredArgsConstructor;

import java.time.Year;

@RequiredArgsConstructor
public class CustomerHaveFreeShippingSpecification implements Specification<Customer> {

    private final Orders orders;

    private final int minPointsForFreeShippingRule1;

    private final long minSalesQuantityForFreeShippingRule1;

    private final int minPointsForFreeShippingRule2;

    @Override
    public boolean isSatisfiedBy(final Customer customer) {
        return customer.getLoyaltyPoints().compareTo(new LoyaltyPoints(minPointsForFreeShippingRule1)) >= 0
                && orders.salesQuantityByCustomerInYear(customer.getId(), Year.now()) >= minSalesQuantityForFreeShippingRule1
                || customer.getLoyaltyPoints().compareTo(new LoyaltyPoints(minPointsForFreeShippingRule2)) >= 0;
    }

}
