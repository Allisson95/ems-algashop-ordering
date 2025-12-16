package com.github.allisson95.algashop.ordering.infrastructure.persistence.provider;

import com.github.allisson95.algashop.ordering.domain.model.entity.ShoppingCart;
import com.github.allisson95.algashop.ordering.domain.model.repository.ShoppingCarts;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.assembler.ShoppingCartPersistenceEntityAssembler;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.disassembler.ShoppingCartPersistenceEntityDisassembler;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.repository.ShoppingCartPersistenceEntityRepository;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.util.DomainVersionHandler;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
class ShoppingCartsPersistenceProvider implements ShoppingCarts {

    private final ShoppingCartPersistenceEntityRepository repository;

    private final ShoppingCartPersistenceEntityAssembler assembler;

    private final ShoppingCartPersistenceEntityDisassembler disassembler;

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void remove(final ShoppingCartId shoppingCartId) {
        this.repository.deleteById(shoppingCartId.value());
    }

    @Transactional
    @Override
    public void remove(final ShoppingCart shoppingCart) {
        this.remove(shoppingCart.id());
    }

    @Override
    public Optional<ShoppingCart> ofId(final ShoppingCartId shoppingCartId) {
        return this.repository.findById(shoppingCartId.value())
                .map(this.disassembler::toDomainEntity);
    }

    @Override
    public boolean exists(final ShoppingCartId shoppingCartId) {
        return this.repository.existsById(shoppingCartId.value());
    }

    @Transactional
    @Override
    public void add(final ShoppingCart shoppingCart) {
        this.repository.findShoppingCartPersistenceEntityWithItemsById(shoppingCart.id().value())
                .ifPresentOrElse(
                        shoppingCartPersistenceEntity -> this.updateShoppingCart(shoppingCartPersistenceEntity, shoppingCart),
                        () -> this.insertShoppingCart(shoppingCart)
                );
    }

    @Override
    public long count() {
        return this.repository.count();
    }

    @Override
    public Optional<ShoppingCart> ofCustomer(final CustomerId customerId) {
        return this.repository.findByCustomer_Id(customerId.value())
                .map(this.disassembler::toDomainEntity);
    }

    private void updateShoppingCart(final ShoppingCartPersistenceEntity shoppingCartPersistenceEntity, final ShoppingCart shoppingCart) {
        this.assembler.merge(shoppingCartPersistenceEntity, shoppingCart);
        this.entityManager.detach(shoppingCartPersistenceEntity);
        this.repository.saveAndFlush(shoppingCartPersistenceEntity);
        this.updateVersion(shoppingCart, shoppingCartPersistenceEntity);
    }

    private void insertShoppingCart(final ShoppingCart shoppingCart) {
        final ShoppingCartPersistenceEntity shoppingCartPersistenceEntity = this.assembler.fromDomain(shoppingCart);
        this.repository.saveAndFlush(shoppingCartPersistenceEntity);
        this.updateVersion(shoppingCart, shoppingCartPersistenceEntity);
    }

    @SneakyThrows
    private void updateVersion(final ShoppingCart shoppingCart, final ShoppingCartPersistenceEntity shoppingCartPersistenceEntity) {
        DomainVersionHandler.setVersion(shoppingCart, shoppingCartPersistenceEntity.getVersion());
        final Map<UUID, Long> shoppingCartItemVersions = shoppingCartPersistenceEntity.getItems().stream()
                .collect(Collectors.toMap(ShoppingCartItemPersistenceEntity::getId, ShoppingCartItemPersistenceEntity::getVersion));
        shoppingCart.items().forEach(item -> DomainVersionHandler.setVersion(item, shoppingCartItemVersions.get(item.id().value())));
    }

}
