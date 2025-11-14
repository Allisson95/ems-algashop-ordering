package com.github.allisson95.algashop.ordering.domain.valueobject;

import org.jspecify.annotations.NonNull;

import java.util.Objects;

public record Document(String value) {

    public Document {
        Objects.requireNonNull(value, "document cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("document cannot be blank");
        }
    }

    @Override
    public @NonNull String toString() {
        return value;
    }

}
