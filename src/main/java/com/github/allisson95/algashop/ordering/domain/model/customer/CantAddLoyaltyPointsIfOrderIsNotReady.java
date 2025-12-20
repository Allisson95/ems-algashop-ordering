package com.github.allisson95.algashop.ordering.domain.model.customer;

import com.github.allisson95.algashop.ordering.domain.model.DomainException;

public class CantAddLoyaltyPointsIfOrderIsNotReady extends DomainException {

    public CantAddLoyaltyPointsIfOrderIsNotReady() {
        super("Cant add loyalty points if order is not ready");
    }

}
