package com.github.allisson95.algashop.ordering.domain.model.valueobject.id;

import com.github.allisson95.algashop.ordering.domain.model.utility.IdGenerator;
import org.jspecify.annotations.NonNull;

import static java.util.Objects.requireNonNull;

public record OrderItemId(String value) {

    public OrderItemId {
        requireNonNull(value, "id cannot be null");
    }

    public OrderItemId() {
        this(IdGenerator.gererateTSID().toString());
    }

    @Override
    public @NonNull String toString() {
        return value;
    }

}
