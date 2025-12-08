package com.github.allisson95.algashop.ordering.infrastructure.persistence.repository;

import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity_;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface OrderPersistenceEntityRepository extends BaseJpaRepository<OrderPersistenceEntity, Long> {

    @EntityGraph(attributePaths = { OrderPersistenceEntity_.ITEMS })
    Optional<OrderPersistenceEntity> findOrderPersistenceEntityWithItemsById(Long id);

}