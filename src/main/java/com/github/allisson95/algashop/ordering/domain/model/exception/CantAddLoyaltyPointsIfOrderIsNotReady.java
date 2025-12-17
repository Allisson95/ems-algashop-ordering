package com.github.allisson95.algashop.ordering.domain.model.exception;

public class CantAddLoyaltyPointsIfOrderIsNotReady extends DomainException {

    public CantAddLoyaltyPointsIfOrderIsNotReady() {
        super("Cant add loyalty points if order is not ready");
    }

}
