package com.github.allisson95.algashop.ordering.domain.model.repository;

import com.github.allisson95.algashop.ordering.domain.model.entity.Customer;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Email;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.CustomerId;

import java.util.Optional;

public interface Customers extends Repository<Customer, CustomerId> {

    Optional<Customer> ofEmail(Email email);

}
