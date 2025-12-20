package com.github.allisson95.algashop.ordering.domain.model.customer;

import com.github.allisson95.algashop.ordering.domain.model.DomainException;

public class CustomerArchivedException extends DomainException {

    public CustomerArchivedException() {
        super("Customer is archived and cannot be updated");
    }

}
