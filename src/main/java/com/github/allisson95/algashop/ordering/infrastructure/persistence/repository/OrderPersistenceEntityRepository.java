package com.github.allisson95.algashop.ordering.infrastructure.persistence.repository;

import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface OrderPersistenceEntityRepository extends BaseJpaRepository<OrderPersistenceEntity, Long> {

}