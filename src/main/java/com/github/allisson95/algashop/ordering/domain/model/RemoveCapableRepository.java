package com.github.allisson95.algashop.ordering.domain.model;

public interface RemoveCapableRepository<T extends AggregateRoot<ID>, ID> extends Repository<T, ID> {

    void remove(ID id);

    void remove(T entity);

}
