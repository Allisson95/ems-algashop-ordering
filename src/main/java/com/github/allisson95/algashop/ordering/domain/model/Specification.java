package com.github.allisson95.algashop.ordering.domain.model;

public interface Specification<T> {

    boolean isSatisfiedBy(T t);

}
