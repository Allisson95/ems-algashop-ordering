package com.github.allisson95.algashop.ordering.domain.model;

public interface AggregateRoot<ID> extends DomainEventSource {

    ID getId();

}
