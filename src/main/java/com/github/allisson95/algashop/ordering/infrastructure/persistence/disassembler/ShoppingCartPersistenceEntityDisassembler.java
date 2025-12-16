package com.github.allisson95.algashop.ordering.infrastructure.persistence.disassembler;

import com.github.allisson95.algashop.ordering.domain.model.entity.ShoppingCart;
import com.github.allisson95.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Money;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.ProductName;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.Quantity;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.github.allisson95.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.github.allisson95.algashop.ordering.infrastructure.persistence.util.DomainVersionHandler;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class ShoppingCartPersistenceEntityDisassembler {

    public ShoppingCart toDomainEntity(final ShoppingCartPersistenceEntity shoppingCartPersistenceEntity) {
        requireNonNull(shoppingCartPersistenceEntity, "shoppingCartPersistenceEntity cannot be null");

        final ShoppingCart shoppingCart = ShoppingCart.existingShoppingCart()
                .id(new ShoppingCartId(shoppingCartPersistenceEntity.getId()))
                .customerId(new CustomerId(shoppingCartPersistenceEntity.getCustomerId()))
                .totalAmount(new Money(shoppingCartPersistenceEntity.getTotalAmount()))
                .totalItems(new Quantity(shoppingCartPersistenceEntity.getTotalItems()))
                .createdAt(shoppingCartPersistenceEntity.getCreatedAt())
                .items(assembleShoppingCartItems(shoppingCartPersistenceEntity.getItems()))
                .build();

        DomainVersionHandler.setVersion(shoppingCart, shoppingCartPersistenceEntity.getVersion());

        return shoppingCart;
    }

    private Set<ShoppingCartItem> assembleShoppingCartItems(final Set<ShoppingCartItemPersistenceEntity> items) {
        if (isNull(items) || items.isEmpty()) {
            return new LinkedHashSet<>();
        }
        return items.stream()
                .map(sci -> {
                    final ShoppingCartItem shoppingCartItem = ShoppingCartItem.existingShoppingCartItem()
                            .id(new ShoppingCartItemId(sci.getId()))
                            .shoppingCartId(new ShoppingCartId(sci.getShoppingCartId()))
                            .productId(new ProductId(sci.getProductId()))
                            .productName(new ProductName(sci.getProductName()))
                            .price(new Money(sci.getPrice()))
                            .quantity(new Quantity(sci.getQuantity()))
                            .totalAmount(new Money(sci.getTotalAmount()))
                            .available(sci.getAvailable())
                            .build();

                    DomainVersionHandler.setVersion(shoppingCartItem, sci.getVersion());

                    return shoppingCartItem;
                })
                .collect(toSet());
    }

}
