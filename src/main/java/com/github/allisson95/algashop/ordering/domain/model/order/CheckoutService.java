package com.github.allisson95.algashop.ordering.domain.model.order;

import com.github.allisson95.algashop.ordering.domain.model.DomainService;
import com.github.allisson95.algashop.ordering.domain.model.product.Product;
import com.github.allisson95.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.github.allisson95.algashop.ordering.domain.model.shoppingcart.ShoppingCartCantProceedToCheckoutException;
import com.github.allisson95.algashop.ordering.domain.model.shoppingcart.ShoppingCartItem;

import static java.util.Objects.requireNonNull;

@DomainService
public class CheckoutService {

    public Order checkout(final ShoppingCart shoppingCart, final Billing billing, final Shipping shipping, final PaymentMethod paymentMethod) {
        requireNonNull(shoppingCart, "Shopping cart cannot be null");
        requireNonNull(billing, "Billing information cannot be null");
        requireNonNull(shipping, "Shipping information cannot be null");
        requireNonNull(paymentMethod, "Payment method cannot be null");

        if (shoppingCart.isEmpty() || shoppingCart.containsUnavailableItems()) {
            throw new ShoppingCartCantProceedToCheckoutException();
        }

        final Order newOrder = Order.draft(shoppingCart.getCustomerId());
        newOrder.changeBilling(billing);
        newOrder.changeShipping(shipping);
        newOrder.changePaymentMethod(paymentMethod);

        for (final ShoppingCartItem sci : shoppingCart.getItems()) {
            final Product product = getProduct(sci);
            newOrder.addItem(product, sci.getQuantity());
        }

        newOrder.place();

        shoppingCart.empty();

        return newOrder;
    }

    private Product getProduct(final ShoppingCartItem sci) {
        return Product.builder()
                .id(sci.getProductId())
                .name(sci.getProductName())
                .price(sci.getPrice())
                .inStock(sci.getAvailable())
                .build();
    }

}
