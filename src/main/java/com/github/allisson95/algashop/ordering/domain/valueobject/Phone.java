package com.github.allisson95.algashop.ordering.domain.valueobject;

import org.jspecify.annotations.NonNull;

import java.util.Objects;

public record Phone(String value) {

    public Phone {
        Objects.requireNonNull(value, "phone cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("phone cannot be blank");
        }
    }

    @Override
    public @NonNull String toString() {
        return value;
    }

}
